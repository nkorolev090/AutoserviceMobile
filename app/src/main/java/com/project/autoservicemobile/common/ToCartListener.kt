package com.project.autoservicemobile.common

import com.project.autoservicemobile.ui.services.models.ServiceUI

interface ToCartListener {
    fun onAddOrRemoveToCart(service: ServiceUI)
}