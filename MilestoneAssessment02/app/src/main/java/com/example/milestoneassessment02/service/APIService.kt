package com.example.milestoneassessment02.service

import com.example.milestoneassessment02.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city:String,
        @Query("appid") apikey:String
    ) : Call<WeatherResponse>
}