package com.project.autoservicemobile.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.home.models.NewsArticleUI
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val titleText: String = "Новости"
    val regsTitle: String = "Записи"
    val regsDescription: String = "Вы можете просмотреть предыдущие записи"

    private var _articles: List<NewsArticleUI> = listOf(
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723145886817-1a2ee70a251b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723083661302-ca5b3459e278?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723249593301-93100100f1e1?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723083661302-ca5b3459e278?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723249593301-93100100f1e1?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723249593301-93100100f1e1?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        NewsArticleUI(
            "How to load Image in ImageView from Url in Android | Android studio | Kotlin",
            "2022",
            "Узнать больше",
            "https://images.unsplash.com/photo-1723145886817-1a2ee70a251b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    )

    val articles = MutableLiveData<List<NewsArticleUI>>().apply {
        value = _articles
    }

    public fun onGoToRegistrationsClick(){
        MAIN.navController.navigate(R.id.action_navigation_home_to_registrationsFragment)
    }
}