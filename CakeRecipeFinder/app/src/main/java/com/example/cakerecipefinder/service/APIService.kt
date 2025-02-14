package com.example.cakerecipefinder.service

import com.example.cakerecipefinder.model.CakeRecipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("search.php")
    fun getCakeRecipes(@Query("s") searchQuery: String): Call<CakeRecipe>
}
