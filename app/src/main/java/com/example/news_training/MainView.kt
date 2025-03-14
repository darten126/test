package com.example.news_training

import com.example.news_training.model.Article

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showNews(newsList: List<Article>)
    fun showError(message: String)
}