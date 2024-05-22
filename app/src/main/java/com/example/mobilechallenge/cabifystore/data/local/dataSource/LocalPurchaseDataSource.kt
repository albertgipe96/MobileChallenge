package com.example.mobilechallenge.cabifystore.data.local.dataSource

import com.example.mobilechallenge.cabifystore.data.local.database.AppDatabase
import com.example.mobilechallenge.cabifystore.data.mappers.PurchaseDataMapper
import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import javax.inject.Provider

interface LocalPurchaseDataSource {
    suspend fun getUserPurchases(): Resource<List<Purchase>>
    suspend fun saveUserPurchase(purchase: Purchase): Resource<Unit>
}

class LocalPurchaseDataSourceImpl(
    private val appDatabaseProvider: Provider<AppDatabase>,
    private val purchaseDataMapper: PurchaseDataMapper
) : LocalPurchaseDataSource {

    private val appDatabase by lazy { appDatabaseProvider.get() }

    private val userPurchaseDao by lazy { appDatabase.userPurchaseDao() }

    override suspend fun getUserPurchases(): Resource<List<Purchase>> = with(purchaseDataMapper) {
        val result = userPurchaseDao.getAllUserPurchases()
        val purchases = result.map { it.toPurchase() }
        return Resource.Success(purchases)
    }

    override suspend fun saveUserPurchase(purchase: Purchase): Resource<Unit> = with(purchaseDataMapper) {
        val result = userPurchaseDao.addNewUserPurchase(purchase.toUserPurchaseEntity())
        return if (result != -1L) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Couldn't insert to database")
        }
    }
}