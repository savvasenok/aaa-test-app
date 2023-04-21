package xyz.savvamirzoyan.allaboutapps.domain.contract

import kotlinx.coroutines.flow.Flow
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain

interface IClubsRepository {

    val clubsFlow: Flow<Result<List<GenericClubInfoDomain>>>

    suspend fun refresh()
    suspend fun getClub(clubId: String): Result<GenericClubInfoDomain>
}