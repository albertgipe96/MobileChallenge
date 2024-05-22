package com.example.mobilechallenge.cabifystore.data.mappers

import com.example.mobilechallenge.cabifystore.data.local.model.UserPurchaseEntity
import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import javax.inject.Inject

class PurchaseDataMapper @Inject constructor() {

    fun Purchase.toUserPurchaseEntity(): UserPurchaseEntity {
        return UserPurchaseEntity(
            amountSpent = amountSpent,
            dateInMillis = dateInMillis
        )
    }

    fun UserPurchaseEntity.toPurchase(): Purchase {
        return Purchase(
            amountSpent = amountSpent,
            dateInMillis = dateInMillis
        )
    }

}