package com.example.newsapp.client

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.service.APIService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.currentsapi.services/v1/"
    private const val LANGUAGE = "en"
    private const val API_KEY = "PDGpVOs2WV4GEo-hKN8EfqRhzICbom0krgS9CDTBS0RYr-Pa"

    private val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    fun getNewsResponse(): Call<NewsResponse> {
        return instance.getNewsResponse(LANGUAGE, API_KEY)
    }

}