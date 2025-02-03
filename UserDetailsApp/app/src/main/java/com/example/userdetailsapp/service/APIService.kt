package com.example.userdetailsapp.service

import com.example.userdetailsapp.model.Todo
import com.example.userdetailsapp.model.User
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("todos")
    fun getTodos(): Call<List<Todo>>
}
