package com.project.autoserviceapi.cart.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemDTO (

    @SerialName("id")
    val id: Int,

    @SerialName("slot")
    val slot: SlotDTO
)