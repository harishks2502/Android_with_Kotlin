package com.example.milestoneassessment02.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String
) : Parcelable

@Parcelize
data class Weather(
    val main: String,
    val description: String
) : Parcelable

@Parcelize
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
) : Parcelable

@Parcelize
data class Wind(
    val speed: Double,
    val deg: Int
) : Parcelable
