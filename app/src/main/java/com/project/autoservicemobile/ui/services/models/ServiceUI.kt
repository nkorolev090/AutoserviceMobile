package com.project.autoservicemobile.ui.services.models

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