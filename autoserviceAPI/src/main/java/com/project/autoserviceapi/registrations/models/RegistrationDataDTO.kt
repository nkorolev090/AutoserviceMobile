package com.project.autoserviceapi.registrations.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDataDTO(
    @SerialName("id")
    val id: Int,

    @SerialName("car_id")
    val carId: Int,

    @SerialName("car_name")
    val carName: String?,

    @SerialName("reg_date")
    val reg_date: String?,

    @SerialName("info")
    val info: String?,

    @SerialName("status")
    val status: Int?,

    @SerialName("status_name")
    val statusName: String?,

    @SerialName("client_id")
    val clientId: Int?,

    @SerialName("reg_price")
    val regPrice: Double?,
)