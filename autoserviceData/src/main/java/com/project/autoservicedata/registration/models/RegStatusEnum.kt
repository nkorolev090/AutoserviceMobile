package com.project.autoservicedata.registration.models

enum class RegStatusEnum {
    PROCESSING,
    APPROVED,
    ABORTED,
    COMPLETED,
    WARRANTY_WORK,
}

fun Int?.toRegStatusEnum(): RegStatusEnum =
    when(this){
        2 -> RegStatusEnum.APPROVED
        3 -> RegStatusEnum.ABORTED
        4 -> RegStatusEnum.COMPLETED
        5 -> RegStatusEnum.WARRANTY_WORK
        else -> {RegStatusEnum.PROCESSING}
    }