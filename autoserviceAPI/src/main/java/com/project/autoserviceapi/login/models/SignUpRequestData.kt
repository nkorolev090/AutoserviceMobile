package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestData (
    @SerialName("name")
    val name: String?,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("passwordConfirm")
    val passwordApply: String
)