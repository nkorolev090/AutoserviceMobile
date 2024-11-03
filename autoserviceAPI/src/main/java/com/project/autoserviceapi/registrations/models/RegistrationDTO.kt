package com.project.autoserviceapi.registrations.models

import com.project.autoserviceapi.cart.models.SlotDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDTO(
    @SerialName("registration")
    val registration: RegistrationDataDTO,

    @SerialName("slots")
    val slots: List<SlotDTO>,
)