package com.project.autoservicedata.cart.models

import com.project.autoserviceapi.cart.models.CartItemDTO

data class CartItem (

    val id: Int,

    val slot: Slot
)

fun CartItemDTO.toCartItem() : CartItem =
    CartItem(
        id = this.id,
        slot = slot.toSlot()
    )
