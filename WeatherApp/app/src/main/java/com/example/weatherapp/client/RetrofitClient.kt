package com.example.weatherapp.client

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.service.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/"
    private const val API_KEY = "c0a44a2f0f0639646481a1ff534d6681"

    val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    fun getWeather(city: String): Call<WeatherResponse> {
        return instance.getWeather(city, API_KEY)
    }
}