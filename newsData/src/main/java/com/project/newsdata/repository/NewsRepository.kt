package com.project.newsdata.repository

import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.newsapi.news.NewsApi
import com.project.newsdata.models.Article
import com.project.newsdata.models.toArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApi
) {

    fun getNews(query: String = QUERY, page: Int = 1, pageSize: Int = PAGE_SIZE, language: String = LANGUAGE): Flow<RequestResult<List<Article>>> {
        return apiRequestFlow {
            api.everything(query,  language, page, pageSize)
        }.map { response ->
            when (response) {
                is RequestResultAPI.Success -> {
//                    saveNetResponseToCache(response.data)
                    if (response.data.articles != null && (response.data.totalResults ?: 0) > 0) {
                        RequestResult.Success(response.data.articles!!.map { it.toArticle() })
                    } else {
                        RequestResult.Error(message = "no_articles")
                    }
                }

                is RequestResultAPI.Error -> {
                    RequestResult.Error(message = response.message, code = response.code)
                    //getFromDatabase(city)
                    //RequestResult.Error(code = response.code, message = response.message)
                }

                is RequestResultAPI.Exception -> {
                    RequestResult.Error(error = response.throwable)
                    //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
                }

                is RequestResultAPI.Loading -> {
                    RequestResult.Loading()
                }
            }
        }
    }

    companion object{
        private const val QUERY = "автомобили"
        private const val PAGE_SIZE = 25
        private const val LANGUAGE = "ru"
    }
}