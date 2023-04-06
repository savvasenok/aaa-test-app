package xyz.savvamirzoyan.allaboutapps.data.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
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
        .map { Result.Success(it) }

    private val _fetchFromCloudActionFlow = MutableSharedFlow<Unit>(replay = 0)
    private val fromCloudClubsFlow = _fetchFromCloudActionFlow
        .map { cloudSource.fetchData().cache() }
        .mapResult { list -> list.map { model -> genericClubInfoDomainMapper.map(model) } }

    override val clubsFlow: Flow<Result<List<GenericClubInfoDomain>>> = merge(fromLocalClubsFlow, fromCloudClubsFlow)
        .debounce { if ((it.getOrNull()?.size ?: 0) == 0) 1500 else 0 }
        .map { it }

    override suspend fun refresh() {

        // TODO: consider checking if new data is the same as initial to exclude multiple rewrite of the same data

        localSource.nukeTable()
        _fetchFromCloudActionFlow.emit()
    }

    override suspend fun getClub(clubName: String): Result<GenericClubInfoDomain> = localSource.select(clubName)
        .let {
            if (it == null) {
                cloudSource.fetchData()
                    .map { list -> list.find { model -> model.name == clubName } }
                    .map { model -> genericClubInfoDomainMapper.map(model) }
            } else {
                Result.Success(genericClubInfoDomainMapper.map(it))
            }
        }

    private suspend fun Result<List<GenericClubInfoCloud>>.cache() = onSuccess { list ->
        list.map { genericClubInfoDomainMapper.mapCloudToLocal(it) }
            .also { localSource.insert(*(it.toTypedArray())) }
    }
}