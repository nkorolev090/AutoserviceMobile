package com.project.autoserviceapi.car.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarDTO (

    @SerialName("id")
    val id: Int,

    @SerialName("model")
    val model: String,

    @SerialName("color")
    val color: String,

    @SerialName("brand")
    val brand: String,

    @SerialName("br_mod")
    val br_mod: String,

    @SerialName("mileage")
    val mileage: Int,

    @SerialName("number")
    val number: String? = null,
)