package com.project.autoserviceapi.deviceToken.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenDTO(
    @SerialName("id")
    val id: Int,

    @SerialName("token")
    val token: String
)