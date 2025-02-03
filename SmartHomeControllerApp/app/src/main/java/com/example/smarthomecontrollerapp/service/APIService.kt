package com.example.smarthomecontrollerapp.service

import com.example.smarthomecontrollerapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}