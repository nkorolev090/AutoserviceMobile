package com.project.autoservicemobile.ui.home.models

import com.project.newsdata.models.Article

data class NewsArticleUI (
    val titleText: String,
    val sourceText: String,
    val contentText: String,
    val btnEnabled: Boolean,
    val urlToImage: String?,
    val url: String,
    val publishedAt: String
)

fun Article.toNewsArticleUI() : NewsArticleUI{
    return NewsArticleUI(
        titleText = this.title ?: "",
        sourceText = this.source ?: "",
        contentText = this.content ?: "",
        btnEnabled = (this.url != null && this.url != ""),
        urlToImage = urlToImage,
        url= this.url ?: "",
        publishedAt = this.publishedAt?.date.toString(),
    )
}