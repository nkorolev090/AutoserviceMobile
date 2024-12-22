package com.project.autoservicemobile.ui.profile.map.models

import com.project.autoservicedata.station.models.Station

data class StationUI(
    val titleText: String,
    val id: Int,
    val scheduleText: String,
    val latitude: Double,
    val longitude: Double,
    val coordinatesText: String,
    val adressText: String,
)

fun Station.toStationUI(): StationUI =
    StationUI(
        titleText = title,
        id = id,
        scheduleText = "Будни: $weekDayWorkTime\nВыходные: $weekEndWorkTime",
        latitude = latitude,
        longitude = longitude,
        coordinatesText = "$latitude, $longitude",
        adressText = adress,
    )

