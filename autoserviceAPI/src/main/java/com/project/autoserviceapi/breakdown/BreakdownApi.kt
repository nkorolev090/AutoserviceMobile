package com.project.autoserviceapi.breakdown

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.autoserviceapi.breakdown.models.BreakdownDTO
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
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakdownApi {
    @GET
    suspend fun getAllBreakdowns(): Response<List<BreakdownDTO>>

    @GET("byQuery")
    suspend fun getBreakdownsByQuery(
        @Query("query") query: String,
    ): Response<List<BreakdownDTO>>
}

fun BreakdownApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json? = null,
): BreakdownApi{
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
