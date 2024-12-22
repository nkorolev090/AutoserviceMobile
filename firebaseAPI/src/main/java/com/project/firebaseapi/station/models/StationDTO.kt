package com.project.firebaseapi.station.models

import com.google.firebase.firestore.GeoPoint


class StationDTO{
    var title: String? = null
    var id: Int? = null
    var weekDayWorkTime: String? = null
    var weekEndWorkTime: String? = null
    var coordinates: GeoPoint? = null
    var adress: String? = null
}