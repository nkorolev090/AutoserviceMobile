package com.project.autoservicemobile

lateinit var MAIN: MainActivity
val rubleSimbol = "₽"


fun Int.toWarrantyText(): String {
    val postfix = when{
        this == 0 -> "Не предусмотрена"
        this in 10..14 || this % 10 in 5..9 -> "Лет"
        this % 10 == 1 -> "Год"
        this % 10 in 2..4 -> "Года"
        else -> "Ошибка"
    }

    return "$this $postfix"
}
