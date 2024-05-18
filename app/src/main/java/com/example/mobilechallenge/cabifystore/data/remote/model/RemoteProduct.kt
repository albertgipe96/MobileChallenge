package com.example.mobilechallenge.cabifystore.data.remote.model

data class RemoteProductData(
    val products: List<RemoteProduct>
)
data class RemoteProduct(
    val code: String,
    val name: String,
    val price: Double
)
