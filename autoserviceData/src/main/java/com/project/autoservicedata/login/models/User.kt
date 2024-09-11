package com.project.autoservicedata.login.models

data class User (
    var id : String,
    var name : String?,
    var surname : String?,
    var midname : String?,
    var email : String?,
    var userName : String?,
    var phoneNumber : String?,
//    var isClient : Boolean,
//
//    var Client : ClientDTO?,
//    var Mechanic : MechanicDTO?
)