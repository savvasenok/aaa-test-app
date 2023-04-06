package xyz.savvamirzoyan.allaboutapps.domain.contract

import kotlinx.coroutines.flow.Flow
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain

interface IClubsRepository {
    fun getClubsFlow(): Flow<Result<List<GenericClubInfoDomain>>>
    suspend fun getClub(clubName: String): Result<GenericClubInfoDomain>
}