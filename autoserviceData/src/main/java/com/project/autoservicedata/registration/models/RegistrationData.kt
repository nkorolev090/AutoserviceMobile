package com.project.autoservicedata.registration.models

import com.project.autoserviceapi.registrations.models.RegistrationDataDTO

data class RegistrationData(
    val id: Int,
    val carId: Int,
    val carName: String?,
    val regDate: String?,
    val info: String?,
    val status: RegStatusEnum,
    val clientId: Int?,
    val regPrice: Double?,
)


fun RegistrationDataDTO.toRegistrationData(): RegistrationData
= RegistrationData(
    id = this.id,
    carId = this.carId,
    carName = this.carName,
    regDate = this.reg_date,
    info = info,
    status = this.status.toRegStatusEnum(),
    clientId = this.clientId,
    regPrice = regPrice
)
