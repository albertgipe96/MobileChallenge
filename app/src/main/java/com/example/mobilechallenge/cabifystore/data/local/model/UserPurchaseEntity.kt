package com.example.mobilechallenge.cabifystore.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPurchases")
data class UserPurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amountSpent: Double,
    val dateInMillis: Long
)