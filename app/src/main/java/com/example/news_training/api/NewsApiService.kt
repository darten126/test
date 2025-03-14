package com.example.news_training.api

import com.example.news_training.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun everything(
        @Query("q") query: String = "bitcoin",
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>

    @GET("v2/top-headlines")
    fun topHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>

    @GET("v2/everything")
    fun everythingFromToDate(
        @Query("q")query:String = "Россия",
        @Query("from")from:String,
        @Query("to")to:String,
        @Query("sortBy")sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}