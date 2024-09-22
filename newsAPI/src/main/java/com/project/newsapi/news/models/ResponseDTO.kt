package com.project.newsapi.news.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO (
    @SerialName("status")
    val status: String?,

    @SerialName("totalResults")
    val totalResults: Int?,

    @SerialName("articles")
    val articles: List<ArticleDTO>?
)