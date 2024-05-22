package com.example.mobilechallenge.cabifystore.domain.model

data class CartProduct(
    val id: Int,
    val code: ProductCode,
    val name: String,
    val price: Double
)
