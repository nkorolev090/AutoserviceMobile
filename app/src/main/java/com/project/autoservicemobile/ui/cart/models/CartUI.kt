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

    val isActive: Boolean,

    val cartItems: List<CartRecycleItem>
)

fun Cart.toCartUI(): CartUI {
    val cartItems: MutableList<CartRecycleItem> = availableCartItems.map { it.toCartItemUI(true) }.toMutableList()

    if(unavailableCartItems.isNotEmpty()){
        cartItems.add(CartRecycleItem.Title("unavailable"))
        cartItems.addAll(unavailableCartItems.map { it.toCartItemUI(false) })
    }
    return CartUI(
        id = this.id,
        total = (this.total.toString() ?: "0") + rubleSimbol,
        subtotal = (this.subtotal.toString() ?: "0") + rubleSimbol,
        promocodeId = this.promocodeId,
        promocodeTitle = this.promocodeTitle,
        discountValue = ("-${this.discountValue}") + rubleSimbol,
        isActive = availableCartItems.isNotEmpty(),
        cartItems = cartItems
    )
}
