package com.example.navigationexample.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val userId: Int
) : Parcelable
