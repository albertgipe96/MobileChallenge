package com.example.mobilechallenge.cabifystore.domain.repositories

import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource

interface ProductsRepository {
    suspend fun getProducts(): Resource<List<Product>>
}