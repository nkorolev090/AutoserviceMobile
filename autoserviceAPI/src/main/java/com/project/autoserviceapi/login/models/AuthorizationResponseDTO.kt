package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponseDTO (
    @SerialName("message")
    val message: String? = null,

    @SerialName("user")
    val user: UserDataDTO? = null,

    @SerialName("userRole")
    val userRole: String? = null,

    @SerialName("token")
    val token: String? = null,
    )