package com.project.newsapi.news.utils

import okhttp3.Interceptor
import okhttp3.Response

class NewsApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .url(
                    chain.request().url.newBuilder()
                        .addQueryParameter("apiKey", apiKey)
                        .build()
                )
                .build()
        return chain.proceed(request)
//        return chain.proceed(
//            chain.request().newBuilder()
//                .addHeader("apiKey", apiKey)
//                .build()
//        )
    }
}