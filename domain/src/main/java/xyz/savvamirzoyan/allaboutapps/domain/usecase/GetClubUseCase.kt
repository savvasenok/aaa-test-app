package xyz.savvamirzoyan.allaboutapps.domain.usecase

import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.Result
import xyz.savvamirzoyan.allaboutapps.domain.contract.IClubsRepository
import xyz.savvamirzoyan.allaboutapps.domain.model.GenericClubInfoDomain
import javax.inject.Inject

interface GetClubUseCase : BaseUseCase.Basic<GetClubUseCaseRequest, GenericClubInfoDomain> {

    class Base @Inject constructor(private val repository: IClubsRepository) : GetClubUseCase {
        override suspend fun invoke(request: GetClubUseCaseRequest): Result<GenericClubInfoDomain> = repository
            .getClub(request.clubId)
    }
}

data class GetClubUseCaseRequest(val clubId: String) : Model.Domain