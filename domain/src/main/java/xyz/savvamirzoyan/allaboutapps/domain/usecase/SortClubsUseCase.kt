package xyz.savvamirzoyan.allaboutapps.domain.usecase

import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import javax.inject.Inject

interface SortClubsUseCase : BaseUseCase.Collection<SortClubsRequestDomain, GenericClubInfoDomain> {

    class Base @Inject constructor() : SortClubsUseCase {

        override suspend fun run(request: SortClubsRequestDomain): Result<List<GenericClubInfoDomain>> =
            when (request.sorting) {
                ClubSortingMethod.NAME -> Result.Success(request.clubs.sortedBy { it.name })
                ClubSortingMethod.VALUE -> Result.Success(request.clubs.sortedBy { it.value })
            }
    }
}

data class SortClubsRequestDomain(
    val clubs: List<GenericClubInfoDomain>,
    val sorting: ClubSortingMethod,
) : Model.Domain

enum class ClubSortingMethod {
    NAME, VALUE
}