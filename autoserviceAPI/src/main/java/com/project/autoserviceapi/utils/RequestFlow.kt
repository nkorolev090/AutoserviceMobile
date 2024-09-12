package com.project.autoserviceapi.utils

import com.google.gson.Gson
import com.project.autoserviceapi.ErrorResponse
import com.project.autoserviceapi.login.RequestResultAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> apiRequestFlow(call: suspend () -> Response<T>): Flow<RequestResultAPI<T>> = flow {
    emit(RequestResultAPI.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(RequestResultAPI.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    val parsedError: ErrorResponse = Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                    emit(RequestResultAPI.Error(parsedError.message, parsedError.code))
                }
            }
        } catch (e: Exception) {
            emit(RequestResultAPI.Exception(e))
        }
    } ?: emit(RequestResultAPI.Error("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)