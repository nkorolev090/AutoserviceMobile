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
        service = if (breakdownId == null) null
        else
            ServiceUI(
                id = this.breakdownId!!,
                title = this.breakdownName ?: "",
                warrantyText = this.breakdownWarranty?.toWarrantyText() ?: "0",
                priceText = (this.cost.toString() ?: "0") + rubleSimbol,
            ),
        mechanicId = this.mechanicId,
        mechanicNameText = this.mechanicName ?: "",
        startTimeText = this.startTime ?: "",
        startDateText = this.startDate ?: "",
        registrationId = registrationId
    )