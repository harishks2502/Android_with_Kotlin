package com.example.ecommerceapp.model

import android.os.Parcelable
import com.example.ecommerceapp.R
import kotlinx.parcelize.Parcelize
import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Double,
    var quantity: Int = 0,
    val imageResourceId: Int = R.drawable.placeholder_image,
    val category: String
)

@Parcelize
data class Cart(
    val products: List<ProductDetail>
) : Parcelable

@Parcelize
data class ProductDetail(
    var quantity: Int,
    val price: Double,
    val name: String
) : Parcelable
