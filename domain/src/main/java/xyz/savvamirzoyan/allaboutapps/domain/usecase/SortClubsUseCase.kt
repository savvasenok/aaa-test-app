package xyz.savvamirzoyan.allaboutapps.domain.usecase

import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import javax.inject.Inject

interface SortClubsUseCase : BaseUseCase.Basic<SortClubsRequestDomain, SortClubsResponseDomain> {

    class Base @Inject constructor() : SortClubsUseCase {
        override suspend fun invoke(request: SortClubsRequestDomain): Result<SortClubsResponseDomain> =
            when (request.sorting) {
                ClubSortingMethod.NAME -> Result.Success(SortClubsResponseDomain(request.clubs.sortedBy { it.name }))
                ClubSortingMethod.VALUE -> Result.Success(SortClubsResponseDomain(request.clubs.sortedBy { it.value }))
            }
    }
}

data class SortClubsRequestDomain(
    val clubs: List<GenericClubInfoDomain>,
    val sorting: ClubSortingMethod,
) : Model.Domain

class SortClubsResponseDomain(val clubs: List<GenericClubInfoDomain>) : Model.Domain

enum class ClubSortingMethod {
    NAME, VALUE
}