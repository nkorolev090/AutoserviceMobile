package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestData (
    @SerialName("name")
    var name: String?,

    @SerialName("email")
    var email: String,

    @SerialName("password")
    var password: String,

    @SerialName("passwordConfirm")
    var passwordApply: String
)