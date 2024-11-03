package com.project.autoservicemobile.ui.profile.models

import com.project.autoservicedata.car.models.Car

data class CarUI (
    val id: Int,
    val model: String,
    val color: String,
    val brand: String,
    val br_mod: String,
    val mileage: String,
    val number: String,
)

fun Car.toCarUI():CarUI =
    CarUI(
        id = id,
        model = model,
        color = color,
        brand = brand,
        br_mod = br_mod,
        mileage = "$mileage км",
        number = number ?: "А111АА37"
    )