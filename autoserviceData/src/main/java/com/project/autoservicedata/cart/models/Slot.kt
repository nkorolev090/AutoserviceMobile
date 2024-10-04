package com.project.autoservicedata.cart.models

import com.project.autoserviceapi.cart.models.SlotDTO

data class Slot (

    val id: Int,

    val breakdownId: Int?,

    val breakdownName: String?,

    val breakdownWarranty: Int?,

    val cost: Double?,

    val mechanicId: Int,

    val mechanicName: String?,

    val startTime: String?,

    val startDate: String?,

    val registrationId: Int?,
)

fun SlotDTO.toSlot(): Slot =
    Slot(
        id = this.id,
        breakdownId = this.breakdownId,
        breakdownName = this.breakdownName,
        breakdownWarranty = this.breakdownWarranty,
        cost = this.cost,
        mechanicId = this.mechanicId,
        mechanicName = this.mechanicName,
        startTime = this.startTime,
        startDate = this.startDate,
        registrationId = registrationId
    )