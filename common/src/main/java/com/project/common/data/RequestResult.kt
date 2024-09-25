package com.project.common.data


sealed class RequestResult<out E: Any>(open val data: E? = null){
    class Loading<E : Any>() : RequestResult<E>()
    class Success<E : Any>(override val data: E) : RequestResult<E>(data)
    class Error<E: Any>(data: E? = null, val code: Any? = null, val message: Any? = null, val error: Throwable? = null) : RequestResult<E>(data)
}
fun <I: Any, O:Any> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    return when(this){
        is RequestResult.Success -> RequestResult.Success(mapper(data))
        is RequestResult.Error -> RequestResult.Error(code = code, message = message)
        is RequestResult.Loading -> RequestResult.Loading()
    }
}

//internal fun <T : Any> Result<T>.toRequestResult(): RequestResult<T> {
//    return when{
//        isSuccess -> RequestResult.Success(getOrThrow())
//        isFailure -> RequestResult.Error()
//        else -> error("Impossible branch")
//    }
//}