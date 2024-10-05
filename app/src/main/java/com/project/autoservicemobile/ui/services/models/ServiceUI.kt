package com.project.autoservicemobile.ui.services.models

import com.project.autoservicedata.breakdown.models.Breakdown
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.toWarrantyText

data class ServiceUI(
    val id: Int,
    val title: String,
    val priceText: String,
    val warrantyText: String,
    val imageUrl: String? = null,
    var inFavourites: Boolean = false,
    var inCart: Boolean = false,
    var inWarranty: Boolean = false,
    val mechanicName: String? = null,
)

fun Breakdown.toServiceUI(): ServiceUI {
    return ServiceUI(
        id = this.id,
        title = this.title,
        priceText = this.price.toString() + rubleSimbol,
        imageUrl = this.imageUrl,
        warrantyText = this.warranty.toWarrantyText()
    )
}