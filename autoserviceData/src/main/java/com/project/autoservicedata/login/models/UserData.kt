package com.project.autoservicedata.login.models

data class UserData (
    val id : String,
    val name : String?,
    val surname : String?,
    val midname : String?,
    val email : String,
    val userName : String,
    val phoneNumber : String?,
//    var isClient : Boolean,
//
//    var Client : ClientDTO?,
//    var Mechanic : MechanicDTO?
)