package xyz.savvamirzoyan.allaboutapps.domain.usecase

import kotlinx.coroutines.flow.Flow
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaaboutapps.core.mapResult
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import javax.inject.Inject

interface GetAllClubsUseCase : BaseUseCase.Flowable<NoParams, GetAllClubsUseCase.GenericClubInfoListDomain> {

    class Base @Inject constructor(private val repository: IClubsRepository) : GetAllClubsUseCase {
        override fun run(request: NoParams): Flow<Result<GenericClubInfoListDomain>> = repository
            .getClubsFlow()
            .mapResult { GenericClubInfoListDomain(it) }
    }

    data class GenericClubInfoListDomain(val clubs: List<GenericClubInfoDomain>) : Model.Domain
}