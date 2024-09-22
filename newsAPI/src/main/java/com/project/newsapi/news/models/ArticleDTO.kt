package com.project.newsapi.news.models

import com.project.common.api.DateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ArticleDTO (
    @SerialName("author")
    val author: String?,

    @SerialName("title")
    val title: String?,

    @SerialName("url")
    val url: String?,

    @SerialName("source")
    val source: SourceDTO?,

    @SerialName("urlToImage")
    val urlToImage: String?,

    @SerialName("content")
    val content: String?,

    @[SerialName("publishedAt") Serializable(with = DateTimeSerializer::class)] val publishedAt: Date?,
)