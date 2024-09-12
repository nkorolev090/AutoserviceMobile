package com.project.autoservicemobile.ui.login.models

import com.project.autoservicedata.login.models.SignInData

data class SignInDataUI (
    var email: String,
    var password: String
)

fun SignInDataUI.toSignInData(): SignInData {
    return SignInData(
        email = this.email,
        password = this.password
    )
}