package xyz.savvamirzoyan.allaaboutapps.core

sealed class Result<T : Any> {

    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error<T : Any>(val code: Int, val message: String?) : Result<T>()
    data class Exception<T : Any>(val throwable: Throwable) : Result<T>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isException: Boolean get() = this is Exception

    fun getOrNull(): T? = (this as? Success)?.data
    fun exceptionOrNull(): Throwable? = (this as? Exception)?.throwable

    inline fun <R : Any> fold(
        onSuccess: (value: T) -> R,
        onError: (code: Int, message: String?) -> R,
        onException: (throwable: Throwable) -> R,
    ): R = when (this) {
        is Error -> onError(code, message)
        is Exception -> onException(throwable)
        is Success -> onSuccess(data)
    }

    inline fun <R : Any> map(transform: (data: T) -> R?): Result<R> = when (this) {
        is Error -> Error(code, message)
        is Exception -> Exception(throwable)
        is Success -> {
            val transformed = transform(data)
            if (transformed != null) Success(transformed)
            else Error(404, "")
        }
    }

    inline fun onSuccess(onSuccess: (data: T) -> Unit): Result<T> {
        if (this is Success) onSuccess(data)
        return this
    }
}