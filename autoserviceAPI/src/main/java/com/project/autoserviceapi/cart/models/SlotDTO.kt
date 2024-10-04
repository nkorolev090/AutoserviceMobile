package com.project.autoserviceapi.cart.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SlotDTO (

    @SerialName("id")
    val id: Int,

    @SerialName("breakdown_id")
    val breakdownId: Int?,

    @SerialName("breakdown_name")
    val breakdownName: String?,

    @SerialName("breakdown_warranty")
    val breakdownWarranty: Int?,

    @SerialName("cost")
    val cost: Double?,

    @SerialName("mechanic_id")
    val mechanicId: Int,

    @SerialName("mechanic_name")
    val mechanicName: String?,

    @SerialName("start_time")
    val startTime: String?,

    @SerialName("start_date")
    val startDate: String?,

    @SerialName("registration_id")
    val registrationId: Int?,
)