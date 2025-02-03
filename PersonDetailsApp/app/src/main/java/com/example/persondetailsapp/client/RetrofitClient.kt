package com.example.persondetailsapp.client

import com.example.persondetailsapp.model.PeopleResponse
import com.example.persondetailsapp.service.APIService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.tvmaze.com/"

    val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    fun searchPeople(query: String): Call<List<PeopleResponse>> {
        return instance.searchPeople(query)
    }

}