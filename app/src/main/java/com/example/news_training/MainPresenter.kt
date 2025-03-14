package com.example.news_training

import android.util.Log
import com.example.news_training.model.NewsResponse
import com.example.newsapp.data.api.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MainPresenter (private val view: MainView) {
    fun loadNews (apiKey: String, query : String){
        view.showLoading()

        RetrofitClient.instance.everything(query = query, apiKey = apiKey)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>){
                    view.hideLoading()

                    response.errorBody()?.string()?.let {
                        Log.e("API_ERROR_BODY", it)
                    }

                    if (response.isSuccessful){
                        response.body()?.articles?.let {
                            view.showNews(it)
                        } ?: view.showError("Ошибка загрузки данных")
                    } else {
                        view.showError("Ошибка сервера: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    view.hideLoading()
                    view.showError("Ошибка сети: ${t.message}")
                }
            })
    }

    fun loadNewsFromToDate (apiKey: String, query: String, from: String, to:String){
        view.showLoading()

        RetrofitClient.instance.everythingFromToDate(query = query, from=from, to = to, apiKey = apiKey, sortBy = "publishedAt")
            .enqueue(object: Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>){
                    view.hideLoading()

                    response.errorBody()?.string()?.let {
                        Log.e("API_ERROR_BODY", it)
                    }
                    Log.d("API_REQUEST", "URL: https://newsapi.org/v2/everything?q=" + query + "&from=" + from + "&to=" + to + "&apiKey=" + apiKey)
                    if(response.isSuccessful){
                        response.body()?.articles?.let {
                            view.showNews(it)
                        } ?: view.showError("Ошибка загрузки данных")
                    } else {
                        view.showError("Ошибка сервера:${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    view.hideLoading()
                    view.showError("Ошибка сети: ${t.message}")
                }
            })
    }

    fun formatDate(isoDate: String?): String{
        if(isoDate.isNullOrEmpty()) return "Без даты"
        else{
            val inputFormat= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(isoDate)
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm",Locale.getDefault())
            return outputFormat.format(date)

        }
    }
}