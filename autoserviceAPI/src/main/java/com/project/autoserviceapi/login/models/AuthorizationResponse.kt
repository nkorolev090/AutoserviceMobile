package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse (
    @SerialName("message")
    var message: String?,

    @SerialName("user")
    var user: UserDTO?,

    @SerialName("userRole")
    var userRole: String?,

    @SerialName("token")
    var token: String?,
    )