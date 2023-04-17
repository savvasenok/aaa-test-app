package xyz.savvamirzoyan.allaaboutapps.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

inline fun <T : Any, R : Any> Flow<Result<T>>.mapResult(crossinline transform: (model: T) -> R): Flow<Result<R>> = this
    .map { result: Result<T> -> result.map { model -> transform(model) } }

inline fun <T : Any> Flow<Result<T>>.onEachSuccess(crossinline block: suspend (model: T) -> Unit): Flow<Result<T>> = this
    .onEach { if (it is Result.Success) block(it.data) }

inline fun <T : Any, R : Any> Flow<Result<T>>.fold(
    crossinline onException: (throwable: Throwable) -> R,
    crossinline onError: (code: Int, message: String?) -> R,
    crossinline onSuccess: (model: T) -> R,
): Flow<Result<R>> = this.map { result ->
    when (result) {
        is Result.Error -> onError(result.code, result.message)
        is Result.Exception -> onException(result.throwable)
        is Result.Success -> onSuccess(result.data)
    }.let { Result.Success(it) }
}

suspend inline fun MutableSharedFlow<Unit>.emit() = this.emit(Unit)