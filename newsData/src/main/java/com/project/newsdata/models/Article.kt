package com.project.newsdata.models

import java.util.Date

data class Article (
    val author: String?,

    val title: String?,

    val source: String?,

    val url: String?,

    val urlToImage: String?,

    val content: String?,

    val publishedAt: Date?,
)