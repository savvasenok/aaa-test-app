package xyz.savvamirzoyan.allaboutapps.networking

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import xyz.savvamirzoyan.allaaboutapps.core.Result

class ResultCall<T : Any>(private val proxy: Call<T>) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) = proxy.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = handleResponse(response)
                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = Result.Exception<T>(t)
                callback.onResponse(this@ResultCall, Response.success(result))
            }
        },
    )

    override fun execute(): Response<Result<T>> = throw NotImplementedError()

    override fun clone(): Call<Result<T>> = ResultCall(proxy)

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun cancel() = proxy.cancel()

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    private fun handleResponse(response: Response<T>): Result<T> = try {
        val body = response.body()

        if (response.isSuccessful && body != null) Result.Success(body)
        else Result.Error(code = response.code(), message = response.message())
    } catch (e: HttpException) {
        Result.Error(e.code(), e.message())
    } catch (e: Throwable) {
        Result.Exception(e)
    }
}