package com.project.autoservicedata.car.models

import com.project.autoserviceapi.car.models.CarDTO

data class Car (
    val id: Int,
    val model: String,
    val color: String,
    val brand: String,
    val br_mod: String,
    val mileage: Int,
    val number: String? = null,
)

fun CarDTO.toCar():Car =
    Car(
        id =id,
        model = model,
        color = color,
        brand = brand,
        br_mod = br_mod,
        mileage = mileage,
        number = number
    )
