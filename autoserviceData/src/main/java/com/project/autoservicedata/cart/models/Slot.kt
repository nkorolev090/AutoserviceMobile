package com.project.autoservicedata.cart.models

import com.project.autoserviceapi.cart.models.SlotDTO
import com.project.common.data.asDateTimeFormatter
import java.time.LocalDateTime
import java.util.Date

data class Slot (

    val id: Int,
    val breakdownId: Int?,
    val breakdownName: String?,
    val breakdownWarranty: Int?,
    val mechanicId: Int,
    val mechanicName: String?,
    val cost: Double?,
    val startDateTime: LocalDateTime?,
    val finishDateTime: LocalDateTime?,
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
        startDateTime = LocalDateTime.parse("$startDate $startTime", asDateTimeFormatter),
        finishDateTime = LocalDateTime.parse("$finishDate $finishTime", asDateTimeFormatter),
        registrationId = registrationId
    )