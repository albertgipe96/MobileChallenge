package com.example.mobilechallenge.cabifystore.domain.model

data class CartProduct(
    val id: Int,
    val code: ProductCode,
    val name: String,
    val price: Double
)

fun CartProduct.toProduct(): Product {
    return Product(code, name, price)
}