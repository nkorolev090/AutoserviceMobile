package com.project.common.firebase

import com.project.common.api.RequestResultAPI
import com.project.common.data.StatusCodeEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull

fun <T : Any?> firetoreRequestFlow(call: suspend () -> T): Flow<RequestResultAPI<T>> = flow {
    emit(RequestResultAPI.Loading)

    withTimeoutOrNull(20000L) {
        try {
            val response = call()
            if (response != null) {
                emit(RequestResultAPI.Success(response))
            } else {
                emit(RequestResultAPI.Error(code = StatusCodeEnum.NO_CONTENT))
            }
        } catch (e: Exception) {
            emit(RequestResultAPI.Exception(e))
        }
    } ?: emit(RequestResultAPI.Error(code = StatusCodeEnum.CONNECTION_TIMED_OUT))
}.flowOn(Dispatchers.IO)