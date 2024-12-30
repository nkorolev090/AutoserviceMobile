package com.project.autoservicemobile.ui.cart.models

import com.project.autoservicedata.cart.models.CartItem

sealed class CartRecycleItem{
    data class Title(val title: String): CartRecycleItem()

    data class CartItemUI (
        val id: Int,
        val isAvailable: Boolean,
        val slot: SlotUI
    ): CartRecycleItem()
}

fun CartItem.toCartItemUI(isAvailable: Boolean) : CartRecycleItem.CartItemUI =
    CartRecycleItem.CartItemUI(
        id = this.id,
        isAvailable = isAvailable,
        slot = slot.toSlotUI()
    )

