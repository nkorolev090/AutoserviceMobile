package com.project.autoservicedata.station.models

import com.project.firebaseapi.station.models.StationDTO

data class Station(
    val title: String,
    val id: Int,
    val weekDayWorkTime: String,
    val weekEndWorkTime: String,
    val latitude: Double,
    val longitude: Double,
    val adress: String,
)

fun StationDTO.toStation(): Station? =
    try{
        Station(
            title = this.title!!,
            id = this.id!!,
            weekDayWorkTime= this.weekDayWorkTime!!,
            weekEndWorkTime = this.weekEndWorkTime!!,
            latitude = this.coordinates!!.latitude,
            longitude = this.coordinates!!.longitude,
            adress = this.adress!!
        )
    }
    catch (ex: NullPointerException){
        null
    }

