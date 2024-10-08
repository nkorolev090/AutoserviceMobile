package com.project.autoservicemobile.ui.registrations.models

import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicedata.registration.models.Registration
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.services.models.ServiceUI

data class RegistrationUI (
    val registrationTitle: String,
    val statusTitle: String,
    val imageURL: String,
    val price: String,
    val services: List<ServiceUI>? = null,
)
fun Registration.toRegistrationUI() : RegistrationUI
 = RegistrationUI(
     registrationTitle = "Запись №${this.id}",
     statusTitle = this.status.toStatusTitle(),
     imageURL = "",
     price = "$regPrice $rubleSimbol"
 )

private fun RegStatusEnum.toStatusTitle(): String {
    return "Not yet implemented"
}
