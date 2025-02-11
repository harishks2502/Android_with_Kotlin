package com.example.todoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val fullName: String? = null,
    val maiID: String? =null,
    val password: String? = null
): Parcelable
