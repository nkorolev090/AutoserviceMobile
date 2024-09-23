package com.project.autoservicedata.breakdown.models

data class Breakdown(
    val id: Int,
    val title: String,
    val info: String?,
    val price: Double,
    val warranty: Int,
    val imageUrl: String?
)