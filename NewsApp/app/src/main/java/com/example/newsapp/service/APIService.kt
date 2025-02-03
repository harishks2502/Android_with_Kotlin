package com.example.newsapp.service

import com.example.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("latest-news")
    fun getNewsResponse(
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}