package com.project.autoservicedata.registration.models

import com.project.autoserviceapi.registrations.models.RegistrationDTO
import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicedata.cart.models.toSlot

data class Registration(
    val registration: RegistrationData,
    val slots: List<Slot>,
)


fun RegistrationDTO.toRegistration(): Registration
= Registration(
    registration = this.registration.toRegistrationData(),
    slots = this.slots.map { it.toSlot() }
)
