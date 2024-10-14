package com.project.autoservicemobile.ui.cart.models

import com.project.autoservicedata.cart.models.Cart
import com.project.autoservicemobile.rubleSimbol

data class CartUI(

    val id: Int,

    val total: String,

    val subtotal: String,

    val promocodeId: Int?,

    val promocodeTitle: String,

    val discountValue: String,

    val cartItems: List<CartItemUI>
)

fun Cart.toCartUI(): CartUI =
    CartUI(
        id = this.id,
        total = (this.total.toString() ?: "0") + rubleSimbol,
        subtotal = (this.subtotal.toString() ?: "0") + rubleSimbol,
        promocodeId = this.promocodeId,
        promocodeTitle = this.promocodeTitle,
        discountValue = ("-${this.discountValue}") + rubleSimbol,
        cartItems = this.cartItems.map { it.toCartItemUI() }
    )
