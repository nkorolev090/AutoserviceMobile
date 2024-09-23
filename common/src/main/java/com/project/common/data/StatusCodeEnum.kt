package com.project.common.data

enum class StatusCodeEnum(val code: Int) {
    OK(200),
    NO_CONTENT(204),
    UNAUTHORIZED(401),
    CONNECTION_TIMED_OUT(522)
}