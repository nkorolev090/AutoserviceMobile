package com.project.autoservicemobile.ui.cart.models

import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.toWarrantyText
import com.project.autoservicemobile.ui.services.models.ServiceUI

data class SlotUI (

    val id: Int,

    var service: ServiceUI?,

    val mechanicId: Int,

    val mechanicNameText: String,

    val startTimeText: String,

    val startDateText: String,

    val registrationId: Int?,

    var selected: Boolean = false
)

fun Slot.toSlotUI(): SlotUI =
    SlotUI(
        id = this.id,
        service = if (this.breakdownId == null) null
        else
            ServiceUI(
                id = this.breakdownId!!,
                title = this.breakdownName ?: "",
                warrantyText = this.breakdownWarranty?.toWarrantyText() ?: "0",
                priceText = (this.cost.toString() ?: "0") + rubleSimbol,
                imageUrl = this.breakdownUrl
            ),
        mechanicId = this.mechanicId,
        mechanicNameText = this.mechanicName ?: "",
        startTimeText = "%02d:%02d".format(this.startDateTime?.hour, this.startDateTime?.minute),
        startDateText = "${this.startDateTime?.dayOfMonth}.${this.startDateTime?.monthValue}.${this.startDateTime?.year}",
        registrationId = registrationId
    )

fun SlotUI.toSlot(): Slot =
    Slot(
        id = this.id,
        breakdownId = this.service?.id,
        breakdownName = this.service?.title,
        breakdownWarranty = null,
        breakdownUrl = this.service?.imageUrl,
        cost = null,
        startDateTime = null,
        finishDateTime = null,
        mechanicId = this.mechanicId,
        mechanicName = this.mechanicNameText,
        registrationId = registrationId,
    )