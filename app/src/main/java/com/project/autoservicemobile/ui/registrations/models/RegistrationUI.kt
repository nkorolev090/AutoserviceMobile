package com.project.autoservicemobile.ui.registrations.models

import com.project.autoservicemobile.ui.services.models.ServiceUI

data class RegistrationUI (
    val registrationTitle: String,
    val completedDate: String,
    val imageURL: String,
    val price: String,
    val services: List<ServiceUI>? = null,
)