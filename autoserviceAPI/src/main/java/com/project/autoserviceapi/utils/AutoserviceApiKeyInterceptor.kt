package com.project.autoserviceapi.utils

import com.project.token.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AutoserviceApiKeyInterceptor(private val tokenManager: TokenManager?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val request =
//            chain.request().newBuilder()
//                .url(
//                    chain.request().url.newBuilder()
//                        .addQueryParameter("X-Api-Key", apiKey)
//                        .build()
//                )
//                .build()
//        return chain.proceed(request)
        val apiKey = tokenManager?.getToken() ?: ""
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", apiKey)
                .build()
        )
    }
}