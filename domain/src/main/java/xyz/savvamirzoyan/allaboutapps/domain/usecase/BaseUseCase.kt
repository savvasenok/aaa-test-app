package xyz.savvamirzoyan.allaboutapps.domain.usecase

import kotlinx.coroutines.flow.Flow
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.Result

sealed interface BaseUseCase {

    interface Basic<in Request : Model.Domain, Data : Model.Domain> : BaseUseCase {
        suspend fun run(request: Request): Result<Data>
    }

    interface Collection<in Request : Model.Domain, Data : Model.Domain> : BaseUseCase {
        suspend fun run(request: Request): Result<List<Data>>
    }

    interface Flowable<in Request : Model.Domain, Data : Model.Domain> : BaseUseCase {
        fun run(request: Request): Flow<Result<Data>>
        suspend fun rerun(request: Request) // created only for asking this flow to re-emit, which is single-method in other 2 UseCases
    }
}

object NoParams : Model.Domain