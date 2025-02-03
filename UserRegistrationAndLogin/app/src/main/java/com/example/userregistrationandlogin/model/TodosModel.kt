package com.example.userregistrationandlogin.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class TodosModel(
    val id: String? = null,
    val taskName: String? = null,
    val taskDescription: String? = null,
    val taskStatus: String? = null
) : Parcelable
