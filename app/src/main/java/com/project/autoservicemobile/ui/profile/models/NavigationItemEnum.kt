package com.project.autoservicemobile.ui.profile.models

import com.project.autoservicemobile.R

enum class NavigationItemEnum {
    FAQ,
    CALL,
    EMAIL
}

fun NavigationItemEnum.toTitleResource(): Int =
    when(this){
        NavigationItemEnum.FAQ -> R.string.faq
        NavigationItemEnum.CALL -> R.string.call
        NavigationItemEnum.EMAIL -> R.string.email
    }

fun NavigationItemEnum.toImageResource(): Int =
    when(this){
        NavigationItemEnum.FAQ -> R.drawable.ic_question
        NavigationItemEnum.CALL -> R.drawable.ic_phone
        NavigationItemEnum.EMAIL -> R.drawable.ic_email
    }