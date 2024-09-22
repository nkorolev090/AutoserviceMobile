package com.project.common.api

sealed class RequestResultAPI<out E>{
    data object Loading: RequestResultAPI<Nothing>()
    data class Success<out E>(val data: E) : RequestResultAPI<E>()
    data class Error(val code: Any? = null,val message: Any? = null) : RequestResultAPI<Nothing>()
    data class Exception(val throwable: Throwable? = null) : RequestResultAPI<Nothing>()
}