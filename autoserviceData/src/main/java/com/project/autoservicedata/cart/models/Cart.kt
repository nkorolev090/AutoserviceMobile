package com.project.autoservicedata.cart.models

import com.project.autoserviceapi.cart.models.CartDTO
import com.project.autoserviceapi.cart.models.CartItemDTO

data class Cart(

    val id: Int,

    val total: Double,

    val subtotal: Double,

    val promocodeId: Int?,

    val promocodeTitle: String,

    val discountValue: Double?,

    val cartItems: List<CartItem>
)

fun CartDTO.toCart(): Cart =
    Cart(
        id = this.id,
        total = this.total,
        subtotal = this.subtotal,
        promocodeId = this.promocodeId,
        promocodeTitle = this.promocodeTitle,
        discountValue = this.discountValue,
        cartItems = this.cartItems.map { it.toCartItem() }
    )
