package com.project.autoservicemobile.ui.services.models

import com.project.autoservicedata.breakdown.models.Breakdown
import com.project.autoservicemobile.rubleSimbol

data class ServiceUI(
    val title: String,
    val priceText: String,
    val imageUrl: String?,
    val warrantyText: String,
    var inFavourites: Boolean = false,
    var inCart: Boolean = false,
    var inWarranty: Boolean = false,
    val mechanicName: String? = null,
)

fun Breakdown.toServiceUI(): ServiceUI {
    return ServiceUI(
        title = this.title,
        priceText = this.price.toString() + rubleSimbol,
        imageUrl = this.imageUrl,
        warrantyText = this.warranty.toWarrantyText()
    )
}

fun Int.toWarrantyText(): String {
    val postfix = when{
        this == 0 -> "Не предусмотрена"
        this in 10..14 || this % 10 in 5..9 -> "Лет"
        this % 10 == 1 -> "Год"
        this % 10 in 2..4 -> "Года"
        else -> "Ошибка"
    }

    return "$this $postfix"
}