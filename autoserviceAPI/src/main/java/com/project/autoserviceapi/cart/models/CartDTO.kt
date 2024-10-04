package com.project.autoserviceapi.cart.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartDTO(

    @SerialName("id")
    val id: Int,

    @SerialName("total")
    val total: Double,

    @SerialName("subtotal")
    val subtotal: Double,

    @SerialName("promocode_id")
    val promocodeId: Int?,

    @SerialName("promocode_title")
    val promocodeTitle: String,

    @SerialName("discount_value")
    val discountValue: Double?,

    @SerialName("cart_items")
    val cartItems: List<CartItemDTO>
)