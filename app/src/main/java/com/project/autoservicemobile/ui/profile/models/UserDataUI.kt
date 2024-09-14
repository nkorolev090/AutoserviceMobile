package com.project.autoservicemobile.ui.profile.models

import com.project.autoservicedata.login.models.UserData

data class UserDataUI(
    var name: String,
    var surname: String,
    var midname: String,
    var email: String,
    var birthDate: String,
    var phoneNumber : String,
    var password: String
)

fun UserData.toUserDataUI(): UserDataUI{
    return UserDataUI(
        name = this.name ?: "-",
        surname = this.surname ?: "-",
        midname = this.midname  ?: "-",
        email = this.email ?: "-",
        birthDate = "-",
        phoneNumber = this.phoneNumber  ?: "-",
        password = "********"
    )
}