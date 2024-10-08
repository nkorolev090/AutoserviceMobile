package com.project.autoserviceapi.registrations

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.autoserviceapi.registrations.models.RegistrationDTO
import com.project.autoserviceapi.utils.AutoserviceApiKeyInterceptor
import com.project.token.TokenManager
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

interface RegistrationApi {
    @GET("getRegistrations")
    suspend fun getRegistrations(): Response<List<RegistrationDTO>>

}

fun RegistrationApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json? = null,
    tokenManager: TokenManager?
): RegistrationApi{
    return retrofit(baseUrl, okHttpClient, json, tokenManager).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json? = null,
    tokenManager: TokenManager?
): Retrofit {
    val jsonConverterFactory = (json ?: Json).asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(AutoserviceApiKeyInterceptor(tokenManager))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
