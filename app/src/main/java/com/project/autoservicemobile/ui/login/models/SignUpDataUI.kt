package com.project.autoservicemobile.ui.login.models

import com.project.autoservicedata.login.models.SignUpData

data class SignUpDataUI(
    var name: String,
    var email: String,
    var password: String
)

fun SignUpDataUI.toSignUpData(): SignUpData {
    return SignUpData(
        name = if (this.name == "" || this.name == "-") null else this.name,
        email = this.email,
        password = this.password,
    )
}