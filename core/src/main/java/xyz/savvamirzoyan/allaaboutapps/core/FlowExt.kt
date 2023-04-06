package xyz.savvamirzoyan.allaaboutapps.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T : Any, R : Any> Flow<Result<T>>.mapResult(crossinline transform: (model: T) -> R): Flow<Result<R>> = this
    .map { result: Result<T> -> result.map { model -> transform(model) } }