package com.example.milestoneassessment02.client

import com.example.milestoneassessment02.model.WeatherResponse
import com.example.milestoneassessment02.service.APIService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URl = "https://api.openweathermap.org/"
    private const val API_KEY = "c0a44a2f0f0639646481a1ff534d6681"

    val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    fun getWeather(city: String): Call<WeatherResponse> {
        return instance.getWeather(city, API_KEY)
    }

}