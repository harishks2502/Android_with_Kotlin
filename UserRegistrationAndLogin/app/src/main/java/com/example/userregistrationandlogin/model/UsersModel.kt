package com.example.userregistrationandlogin.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UsersModel(
    val mailID: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null
) : Parcelable
