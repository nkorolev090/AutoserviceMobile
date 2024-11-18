package com.project.autoservicemobile.ui.services.models

import com.project.autoservicedata.products.models.Product
import com.project.autoservicemobile.rubleSimbol

data class ProductUI(
    var articleText: String,
    var forAutoText: String,
    var imageUrl: String,
    var priceText: String,
    var titleText: String,
    var brandText: String,
    var url: String,
)

fun Product.toProductUI()=
    ProductUI(
        articleText = article,
        forAutoText = "Для: $forAuto",
        imageUrl = imageUrl,
        priceText = "$price $rubleSimbol",
        titleText = title,
        brandText = brand,
        url = url
    )