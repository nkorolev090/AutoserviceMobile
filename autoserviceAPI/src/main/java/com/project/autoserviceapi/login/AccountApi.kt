@file:Suppress("unused")

package com.project.autoserviceapi.login

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.autoserviceapi.login.models.AuthorizationResponseDTO
import com.project.autoserviceapi.login.models.SignInRequestData
import com.project.autoserviceapi.login.models.SignUpRequestData
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AccountApi {
    @POST("login")
    suspend fun loginResponse(
        @Body model: SignInRequestData,
    ): Response<AuthorizationResponseDTO>

    @POST("register")
    suspend fun logupResponse(
        @Body model: SignUpRequestData,
    ): Response<AuthorizationResponseDTO>

    @GET("isauthenticated")
    suspend fun isAuthenticatedResponse(
        @Header("Authorization") token: String,
    ): Response<AuthorizationResponseDTO>
}

//suspend fun <T : Any> handleApi(
//    execute: suspend () -> Response<T>
//): RequestResultAPI<T> {
//    return try {
//        val response= execute()
//        val body = response.body()
//        if (response.isSuccessful) {
//            RequestResultAPI.Success(data = checkNotNull(body))
//        } else {
//            RequestResultAPI.Error(code = response.code(), message = response.message())
//        }
//    } catch (e: HttpException) {
//        RequestResultAPI.Error(code = e.code(), message = e.message())
//    } catch (e: Throwable) {
//        RequestResultAPI.Exception(throwable = e)
//    }
//}

sealed class RequestResultAPI<out E>{
    data object Loading: RequestResultAPI<Nothing>()
    data class Success<out E>(val data: E) : RequestResultAPI<E>()
    data class Error(val code: Any? = null,val message: Any? = null) : RequestResultAPI<Nothing>()
    data class Exception(val throwable: Throwable? = null) : RequestResultAPI<Nothing>()
}

fun AccountApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): AccountApi{
    return retrofit(baseUrl, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
    ): Retrofit {
    val jsonConverterFactory = Json{
        explicitNulls = false
        isLenient = true
        ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
