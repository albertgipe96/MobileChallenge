package com.example.mobilechallenge.cabifystore.data.repositories

import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalPurchaseDataSource
import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.PurchasesRepository
import javax.inject.Inject

class PurchasesRepositoryImpl @Inject constructor(
    private val localPurchaseDataSource: LocalPurchaseDataSource
) : PurchasesRepository {

    override suspend fun getPurchases(): Resource<List<Purchase>> {
        return localPurchaseDataSource.getUserPurchases()
    }

    override suspend fun addPurchase(purchase: Purchase): Resource<Unit> {
        return localPurchaseDataSource.saveUserPurchase(purchase)
    }
}