package xyz.savvamirzoyan.allaboutapps.networking

import retrofit2.Call
import retrofit2.CallAdapter
import xyz.savvamirzoyan.allaaboutapps.core.Result
import java.lang.reflect.Type

class ResultCallAdapter(
    private val resultType: Type,
) : CallAdapter<Type, Call<Result<Type>>> {

    override fun responseType() = resultType
    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)
}