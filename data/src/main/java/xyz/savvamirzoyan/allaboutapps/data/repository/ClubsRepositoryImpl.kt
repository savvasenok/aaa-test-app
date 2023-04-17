package xyz.savvamirzoyan.allaboutapps.data.repository

import android.util.Log
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaaboutapps.core.emit
import xyz.savvamirzoyan.allaaboutapps.core.mapResult
import xyz.savvamirzoyan.allaboutapps.data.mapper.GenericClubInfoDomainMapper
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud
import xyz.savvamirzoyan.allaboutapps.networking.services.ApiService
import xyz.savvamirzoyan.allaboutapps.storage.dao.GenericClubInfoDao
import javax.inject.Inject

@OptIn(FlowPreview::class)
class ClubsRepositoryImpl @Inject constructor(
    private val cloudSource: ApiService,
    private val localSource: GenericClubInfoDao,
    private val genericClubInfoDomainMapper: GenericClubInfoDomainMapper,
) : IClubsRepository {

    private val fromLocalClubsFlow: Flow<Result.Success<List<GenericClubInfoDomain>>> = localSource.selectAllFlow()
        .map { list -> list.map { genericClubInfoDomainMapper.map(it) } }
        .distinctUntilChanged { old, new -> old == new }
        .map { Result.Success(it) }
        .onEach { Log.d("SPAMEGGS", "LocalSource: ${it.data.size} >>> ${it.data}") }

    private val _fetchFromCloudActionFlow = MutableSharedFlow<Unit>(replay = 1)
    private val fromCloudClubsFlow = _fetchFromCloudActionFlow
        .map { cloudSource.fetchData().cache() }
        .mapResult { list -> list.map { model -> genericClubInfoDomainMapper.map(model) } }
        .onEach { Log.d("SPAMEGGS", "CloudSource: ${it.getOrNull()?.size} >>> $it") }

    override val clubsFlow: Flow<Result<List<GenericClubInfoDomain>>> =
        combine(fromLocalClubsFlow, fromCloudClubsFlow) { local, cloud ->
            if (cloud.isSuccess) cloud
            else if (local.data.isNotEmpty()) local
            else cloud
        }
            .onStart { refresh() }
            .onEach { Log.d("SPAMEGGS", "Merged Source: ${it.getOrNull()?.size} >>> ${it.getOrNull()}") }

    override suspend fun refresh() {
        // TODO: consider checking if new data is the same as initial to exclude multiple rewrite of the same data
        _fetchFromCloudActionFlow.emit()
    }

    override suspend fun getClub(clubName: String): Result<GenericClubInfoDomain> = localSource.select(clubName)
        ?.let { Result.Success(genericClubInfoDomainMapper.map(it)) }
        ?: cloudSource.fetchData()
            .map { list -> list.find { model -> model.name == clubName } }
            .map { model -> genericClubInfoDomainMapper.map(model) }

    private suspend fun Result<List<GenericClubInfoCloud>>.cache() = onSuccess { list ->
        localSource.nukeTable()
        list.map { genericClubInfoDomainMapper.mapCloudToLocal(it) }
            .also { localSource.insert(*(it.toTypedArray())) }
            .also { Log.d("SPAMEGGS", "LocalSource cached: ${it.size} >>> $it") }
    }
}