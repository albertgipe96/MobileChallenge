package com.example.mobilechallenge.cabifystore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mobilechallenge.cabifystore.data.local.model.UserPurchaseEntity

@Dao
interface UserPurchaseDao {

    @Query("SELECT * FROM userPurchases")
    fun getAllUserPurchases(): List<UserPurchaseEntity>

    @Insert
    fun addNewUserPurchase(userPurchase: UserPurchaseEntity): Long

}