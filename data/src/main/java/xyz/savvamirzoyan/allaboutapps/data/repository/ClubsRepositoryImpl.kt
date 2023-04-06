package xyz.savvamirzoyan.allaboutapps.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.data.mapper.GenericClubInfoDomainMapper
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import xyz.savvamirzoyan.allaboutapps.networking.model.GenericClubInfoCloud
import xyz.savvamirzoyan.allaboutapps.networking.services.ApiService
import xyz.savvamirzoyan.allaboutapps.storage.dao.GenericClubInfoDao
import javax.inject.Inject

class ClubsRepositoryImpl @Inject constructor(
    private val cloudSource: ApiService,
    private val localSource: GenericClubInfoDao,
    private val genericClubInfoDomainMapper: GenericClubInfoDomainMapper,
) : IClubsRepository {

    override fun getClubsFlow(): Flow<Result<List<GenericClubInfoDomain>>> = flow {
        // first take cached value if exists
        val local = localSource.selectAll()
            .map { genericClubInfoDomainMapper.map(it) }
            .let { Result.Success(it) }
        emit(local)

        // finish with most recent data from API and cache data
        val cloud = cloudSource.fetchData()
            .cache()
            .map { list -> list.map { model -> genericClubInfoDomainMapper.map(model) } }
        emit(cloud)
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