package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientDTO (
    @SerialName("id")
    val id: Int,

    @SerialName("discount_name")
    val discountName: String,

    @SerialName("discount_points")
    val discountPoints: Int,

    @SerialName("default_station_id")
    val defaultStationId: Int?,

    @SerialName("birth_short")
    val birthday: String
)