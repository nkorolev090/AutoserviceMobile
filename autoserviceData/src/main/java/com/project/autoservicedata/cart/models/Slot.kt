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
    val breakdownUrl: String?,
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
        breakdownUrl = this.breakdownUrl,
        cost = this.cost,
        mechanicId = this.mechanicId,
        mechanicName = this.mechanicName,
        startDateTime = LocalDateTime.parse("$startDate $startTime", asDateTimeFormatter),
        finishDateTime = LocalDateTime.parse("$finishDate $finishTime", asDateTimeFormatter),
        registrationId = registrationId
    )

fun Slot.toSlotDTO(): SlotDTO =
    SlotDTO(
        id = this.id,
        breakdownId = this.breakdownId,
        breakdownName = this.breakdownName,
        breakdownWarranty = this.breakdownWarranty,
        breakdownUrl = this.breakdownUrl,
        cost = this.cost,
        mechanicId = this.mechanicId,
        mechanicName = this.mechanicName,
//        startDate = "${this.startDateTime!!.dayOfMonth}.${this.startDateTime.monthValue}.${this.startDateTime.year}",
//        finishDate = "${this.finishDateTime!!.dayOfMonth}.${this.finishDateTime.monthValue}.${this.finishDateTime.year}",
//        startTime = "${this.startDateTime.minute}:${this.startDateTime.hour}",
//        finishTime = "${this.finishDateTime.minute}:${this.finishDateTime.hour}",
        startTime = null,
        startDate = null,
        finishDate = null,
        finishTime = null,
        registrationId = registrationId
    )