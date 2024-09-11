package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestData (
    @SerialName("email")
    var email: String,

    @SerialName("password")
    var password: String,

    @SerialName("rememberMe")
    var rememberMe: Boolean
)