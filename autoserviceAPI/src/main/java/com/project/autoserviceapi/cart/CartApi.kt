package com.project.autoserviceapi.cart

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.autoserviceapi.breakdown.models.BreakdownDTO
import com.project.autoserviceapi.cart.models.CartDTO
import com.project.autoserviceapi.cart.models.CartItemDTO
import com.project.autoserviceapi.cart.models.SlotDTO
import com.project.autoserviceapi.utils.AutoserviceApiKeyInterceptor
import com.project.token.TokenManager
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface CartApi {
    @GET("GetCart")
    suspend fun getCart(): Response<CartDTO>

    @PUT("AddCartItem")
    suspend fun addCartItem(
        @Query("slotId") slotId: Int,
        @Query("breakdownId") breakdownId: Int
    ): Response<CartDTO>

    @PUT("RemoveCartItem")
    suspend fun removeCartItem(
        @Query("breakdownId") breakdownId: Int,
    ): Response<CartDTO>

    @PUT("SetPromocode")
    suspend fun setPromocode(
        @Body promocode: String,
    ): Response<CartDTO>
}

fun CartApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json? = null,
    tokenManager: TokenManager?
): CartApi{
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
