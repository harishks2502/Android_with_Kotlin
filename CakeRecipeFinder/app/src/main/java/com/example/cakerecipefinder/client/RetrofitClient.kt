package com.example.cakerecipefinder.client

import com.example.cakerecipefinder.model.CakeRecipe
import com.example.cakerecipefinder.service.APIService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    private val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    fun getCakeRecipes(): Call<CakeRecipe> {
        return instance.getCakeRecipes("Cake")
    }

}
