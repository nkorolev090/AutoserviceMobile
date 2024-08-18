package com.project.autoservicemobile.ui.services.models

data class ServiceUI(
    val title: String,
    var priceText: String,
    val imageUrl: String,
    var inFavourites: Boolean,
    var inCart: Boolean = false,
    var inWarranty: Boolean = false,
    val mechanicName: String? = null,
)