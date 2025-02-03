package com.example.newsapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class NewsResponse(
    val status: String,
    val news: List<News>,
    val page: Int
) : Parcelable

@Parcelize
data class News(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val author: String,
    val image: String,
    val language: String,
    val category: List<String>,
    val published: String
) : Parcelable