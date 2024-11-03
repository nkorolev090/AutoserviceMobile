package com.project.autoservicemobile.ui.registrations.models

import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicedata.registration.models.Registration
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.autoservicemobile.ui.cart.models.toSlotUI

data class RegistrationUI(
    val registrationTitle: String,
    val registrationNumber: String,
    val statusTitle: String,
    val status: RegStatusEnum,
    val price: String,
    val slots: List<SlotUI>? = null,
)

fun Registration.toRegistrationUI(): RegistrationUI = RegistrationUI(
    registrationTitle = "Запись от ${this.registration.regDate}",
    registrationNumber = "№${this.registration.id}",
    statusTitle = this.registration.status.toStatusTitle(),
    status = this.registration.status,
    price = "$registration.regPrice $rubleSimbol",
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
