package com.example.userdetailsapp.client

import com.example.userdetailsapp.service.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val instance: APIService by lazy {
        Retrofit.Builder() //builder
            .baseUrl(BASE_URL)//baseUrl
            .addConverterFactory(GsonConverterFactory.create()) //handle response
            .build() // build the Service
            .create(APIService::class.java) // for which interface APIService
    }
}