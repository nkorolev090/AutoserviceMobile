package com.project.autoservicemobile.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.home.models.NewsArticleUI
import com.project.autoservicemobile.ui.home.models.toNewsArticleUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import com.project.newsdata.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val _accountRepository: AccountRepository,
    private val _newsRepository: NewsRepository
) : BaseViewModel() {

    val titleText: String = "Новости"
    val regsTitle: String = "Записи"
    val regsDescription: String = "Вы можете просмотреть предыдущие записи"

    var newsPage = 2
    private var _inProgress = false

    val articles = MutableLiveData<RequestResult<List<NewsArticleUI>>>().apply {
        value = RequestResult.Loading()
    }

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun updateArticles(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        articles,
        coroutinesErrorHandler,
        request = { _newsRepository.getNews() },
        mapper = { data -> data.map { list -> list.map { it.toNewsArticleUI() } } }
    )

    //refactor!!!
    fun loadMoreArticles(){
        if(_inProgress.not()){
            Log.d("HomeVM", "load more")
            _inProgress = true
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    _newsRepository.getNews(page = newsPage).collect{
                        withContext(Dispatchers.Main){
                            when(it){
                                is RequestResult.Error -> {}
                                is RequestResult.Loading -> {}
                                is RequestResult.Success -> {
                                    if(articles.value?.data != null && it.data.isNotEmpty()){
                                       articles.postValue(
                                           RequestResult.Success(
                                               listOf(
                                                   *articles.value!!.data!!.toTypedArray(),
                                                   *it.data.map { article -> article.toNewsArticleUI() }.toTypedArray()
                                               )
                                           )
                                           )
                                        newsPage++
                                        _inProgress = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { _accountRepository.isAuthenticated() },
    )
}