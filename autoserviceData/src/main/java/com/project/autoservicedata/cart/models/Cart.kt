package com.project.autoservicedata.cart.models

import com.project.autoserviceapi.cart.models.CartDTO

data class Cart(

    val id: Int,

    val total: Double,

    val subtotal: Double,

    val promocodeId: Int?,

    val promocodeTitle: String,

    val discountValue: Double?,

    val availableCartItems: List<CartItem>,

    val unavailableCartItems: List<CartItem>
)

fun CartDTO.toCart(): Cart =
    Cart(
        id = this.id,
        total = this.total,
        subtotal = this.subtotal,
        promocodeId = this.promocodeId,
        promocodeTitle = this.promocodeTitle,
        discountValue = this.discountValue,
        availableCartItems = this.availableCartItems.map { it.toCartItem() },
        unavailableCartItems = this.unavailableCartItems.map { it.toCartItem() }
    )
