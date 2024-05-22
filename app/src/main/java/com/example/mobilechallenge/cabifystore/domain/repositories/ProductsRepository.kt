package com.example.mobilechallenge.cabifystore.domain.repositories

import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource

interface ProductsRepository {
    suspend fun getProducts(): Resource<List<Product>>
    suspend fun getCartProducts(): Resource<List<CartProduct>>
    suspend fun addProductToCart(product: Product): Resource<Unit>
}