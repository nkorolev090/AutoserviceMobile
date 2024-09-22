@file:Suppress("unused")

package com.project.newsapi.news

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.newsapi.news.models.ResponseDTO
import com.project.newsapi.news.utils.NewsApiKeyInterceptor
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.intellij.lang.annotations.Language
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun everything(
        @Query("q") query: String,
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?,
    ): Response<ResponseDTO>

}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    apiKey: String,
    json: Json? = null,
): NewsApi{
    return retrofit(baseUrl, okHttpClient, apiKey, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    apiKey: String,
    json: Json? = null,
    ): Retrofit {
    val jsonConverterFactory = (json ?: Json).asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(NewsApiKeyInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
