package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataDTO (
    @SerialName("id")
    val id : String,

    @SerialName("name")
    val name : String?,

    @SerialName("surname")
    val surname : String?,

    @SerialName("midname")
    val midname : String?,

    @SerialName("email")
    val email : String,

    @SerialName("userName")
    val userName : String,

    @SerialName("phoneNumber")
    val phoneNumber : String?,

    @SerialName("isClient")
    val isClient : Boolean?,
//
//    var Client : ClientDTO?,
//    var Mechanic : MechanicDTO?
)