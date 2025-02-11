package com.example.todoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id: String? = null,
    val taskName: String? = null,
    val taskDescription: String? = null,
    val taskStatus: String? = null
) : Parcelable
