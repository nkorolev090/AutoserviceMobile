package com.project.autoservicemobile.ui.cart.models

import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.toWarrantyText

data class SlotUI (

    val id: Int,

    val breakdownId: Int?,

    val breakdownName: String,

    val breakdownWarrantyText: String,

    val costText: String,

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
        breakdownId = this.breakdownId,
        breakdownName = this.breakdownName ?: "",
        breakdownWarrantyText = this.breakdownWarranty?.toWarrantyText() ?: "0",
        costText = (this.cost.toString() ?: "0") + rubleSimbol,
        mechanicId = this.mechanicId,
        mechanicNameText = this.mechanicName ?: "",
        startTimeText = this.startTime ?: "",
        startDateText = this.startDate ?: "",
        registrationId = registrationId
    )