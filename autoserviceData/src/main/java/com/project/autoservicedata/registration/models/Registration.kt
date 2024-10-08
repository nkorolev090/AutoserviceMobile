package com.project.autoservicedata.registration.models

import com.project.autoserviceapi.registrations.models.RegistrationDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Registration(
    val id: Int,
    val carId: Int,
    val carName: String?,
    val reg_date: String?,
    val info: String?,
    val status: RegStatusEnum,
    val clientId: Int?,
    val regPrice: Double?,
)


fun RegistrationDTO.toRegistration() : Registration{
    TODO("Not yet implemented")
}