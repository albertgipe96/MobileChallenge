package com.example.mobilechallenge.cabifystore.domain.repositories

import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.Resource

interface PurchasesRepository {
    suspend fun getPurchases(): Resource<List<Purchase>>
    suspend fun addPurchase(purchase: Purchase): Resource<Unit>
}