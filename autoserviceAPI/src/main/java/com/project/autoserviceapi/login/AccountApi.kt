@file:Suppress("unused")

package com.project.autoserviceapi.login

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.autoserviceapi.login.models.AuthorizationResponseDTO
import com.project.autoserviceapi.login.models.ClientDTO
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
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

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

    @PATCH("updateDefaultStation")
    suspend fun updateDefaultStation(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<ClientDTO>
}

fun AccountApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json? = null,
): AccountApi{
    return retrofit(baseUrl, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json? = null,
    ): Retrofit {
    val jsonConverterFactory = (json ?: Json).asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
