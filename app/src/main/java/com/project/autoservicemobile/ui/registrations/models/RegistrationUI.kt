package com.project.autoservicemobile.ui.registrations.models

import android.content.Context
import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicedata.registration.models.Registration
import com.project.autoservicemobile.R
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.autoservicemobile.ui.cart.models.toSlotUI

data class RegistrationUI(
    val id: Int,
    val registrationTitle: String,
    val registrationNumber: String,
    val startOrFinishDate: String,
    val statusTitle: String,
    val status: RegStatusEnum,
    val price: String,
    val slots: List<SlotUI>? = null,
)

fun Registration.toRegistrationUI(): RegistrationUI = RegistrationUI(
    id = this.registration.id,
    registrationTitle = "Запись от ${this.registration.regDate}",
    registrationNumber = "№${this.registration.id}",
    startOrFinishDate = "Начало работ 10.10.2024",
    statusTitle = this.registration.status.toStatusTitle(),
    status = this.registration.status,
    price = "${registration.regPrice} $rubleSimbol",
    slots = this.slots.map { it.toSlotUI() }
)

private fun RegStatusEnum.toStatusTitle(): String =
    when (this) {
        RegStatusEnum.PROCESSING -> "в обработке"
        RegStatusEnum.APPROVED -> "одобрена"
        RegStatusEnum.ABORTED -> "отклонена"
        RegStatusEnum.COMPLETED -> "завершена"
        RegStatusEnum.WARRANTY_WORK -> "гарантия"
    }

fun RegStatusEnum.toStatusColor(context : Context): Int =
    when (this) {
        RegStatusEnum.PROCESSING -> context.getColor(R.color.yellow_gray_300)
        RegStatusEnum.APPROVED -> context.getColor(R.color.green_gray_300)
        RegStatusEnum.ABORTED -> context.getColor(R.color.red_gray_300)
        RegStatusEnum.COMPLETED -> context.getColor(R.color.teal_gray_200)
        RegStatusEnum.WARRANTY_WORK -> context.getColor(R.color.orange_gray_300)
    }