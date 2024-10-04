package com.project.autoservicemobile.ui.cart.models

import com.project.autoservicedata.cart.models.CartItem

data class CartItemUI (

    val id: Int,

    val slot: SlotUI
)

fun CartItem.toCartItemUI() : CartItemUI =
    CartItemUI(
        id = this.id,
        slot = slot.toSlotUI()
    )
