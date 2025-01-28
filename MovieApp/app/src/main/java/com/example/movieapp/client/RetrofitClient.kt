package com.example.movieapp.client

import com.example.movieapp.model.MovieResponse
import com.example.movieapp.service.APIService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "646c8909b78e39a35a87903f0002e8a8"

    val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    fun getPopularMovies(): Call<MovieResponse> {
        return instance.getPopularMovies(API_KEY)
    }

}
