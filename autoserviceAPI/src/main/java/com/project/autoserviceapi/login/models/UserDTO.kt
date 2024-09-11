package com.project.autoserviceapi.login.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    @SerialName("id")
    var id : String,

    @SerialName("name")
    var name : String?,

    @SerialName("surname")
    var surname : String?,

    @SerialName("midname")
    var midname : String?,

    @SerialName("email")
    var email : String?,

    @SerialName("userName")
    var userName : String?,

    @SerialName("phoneNumber")
    var phoneNumber : String?,

    @SerialName("isClient")
    var isClient : Boolean?,
//
//    var Client : ClientDTO?,
//    var Mechanic : MechanicDTO?
)