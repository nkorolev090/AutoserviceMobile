package com.project.autoservicemobile.ui.services.models

import com.project.autoservicemobile.R

enum class SearchItemEnum {
    SERVICES,
    PRODUCTS
}

fun SearchItemEnum.toTitleResource(): Int =
    when (this) {
        SearchItemEnum.SERVICES -> R.string.services_title
        SearchItemEnum.PRODUCTS -> R.string.products_title
    }