package com.project.autoserviceapi.breakdown.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreakdownDTO(
    @SerialName("id")
    val id : Int,

    @SerialName("title")
    val title : String,

    @SerialName("info")
    val info : String?,

    @SerialName("price")
    val price: Double,

    @SerialName("warranty")
    val warranty: Int,

    @SerialName("imageUrl")
    val imageUrl: String?
)