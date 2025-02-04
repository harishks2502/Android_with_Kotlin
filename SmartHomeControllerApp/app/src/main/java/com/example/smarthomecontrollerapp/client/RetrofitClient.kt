package com.example.smarthomecontrollerapp.client

import com.example.smarthomecontrollerapp.model.WeatherResponse
import com.example.smarthomecontrollerapp.service.APIService
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

    suspend fun getWeather(city: String = "Hassan"): WeatherResponse {
        return instance.getWeather(city, API_KEY)
    }
}