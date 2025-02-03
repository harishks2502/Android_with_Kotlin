package com.example.persondetailsapp.service

import com.example.persondetailsapp.model.PeopleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("search/people")
    fun searchPeople(@Query("q") query: String): Call<List<PeopleResponse>>
}