package com.project.newsdata.models

import com.project.newsapi.news.models.ArticleDTO

public fun ArticleDTO.toArticle(): Article{
    return Article(
        author = this.author,
        title = this.title,
        source = this.source?.name,
        url = this.url,
        urlToImage = this.urlToImage,
        content = this.content,
        publishedAt = this.publishedAt
    )
}