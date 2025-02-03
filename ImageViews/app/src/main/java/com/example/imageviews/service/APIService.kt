package com.example.imageviews.service

import com.example.imageviews.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("photos")
    //fun getPhotos(): Call<List<Image>>
    fun getPhotos(@Query("_limit") limit: Int = 10): Call<List<Image>>
}