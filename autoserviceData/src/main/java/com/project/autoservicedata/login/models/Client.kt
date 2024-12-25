package com.project.autoservicedata.login.models

data class Client(
    val id: Int,
    val discountName: String,
    val discountPoints: Int,
    val defaultStationId: Int?,
    val birthday: String
)