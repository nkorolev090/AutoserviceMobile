package com.project.autoservicemobile.ui.home

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.home.models.NewsArticleUI
import com.project.autoservicemobile.ui.home.models.toNewsArticleUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import com.project.newsdata.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val titleText: String = "Новости"
    val regsTitle: String = "Записи"
    val regsDescription: String = "Вы можете просмотреть предыдущие записи"

    val articles = MutableLiveData<RequestResult<List<NewsArticleUI>>>().apply {
        value = RequestResult.Loading()
    }

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun updateArticles(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        articles,
        coroutinesErrorHandler,
        request = { newsRepository.getNews() },
        mapper = { data -> data.map { list -> list.map { it.toNewsArticleUI() } } }
    )

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )

    fun onGoToRegistrationsClick(){
        MAIN.navController.navigate(R.id.action_navigation_home_to_registrationsFragment)
    }
}