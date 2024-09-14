package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestData (
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("rememberMe")
    val rememberMe: Boolean
)