package com.example.persondetailsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeopleResponse(
    val score: Double,
    val person: Person
) : Parcelable

@Parcelize
data class Person(
    val id: Int,
    val url: String,
    val name: String,
    val country: Country?,
    val birthday: String?,
    val deathday: String?,
    val gender: String?,
    val image: Image?,
    val updated: Long,
    @SerializedName("_links") val links: Links
) : Parcelable

@Parcelize
data class Country(
    val name: String,
    val code: String,
    val timezone: String
) : Parcelable

@Parcelize
data class Image(
    val medium: String,
    val original: String
) : Parcelable

@Parcelize
data class Links(
    val self: Link
) : Parcelable

@Parcelize
data class Link(
    val href: String
) : Parcelable
