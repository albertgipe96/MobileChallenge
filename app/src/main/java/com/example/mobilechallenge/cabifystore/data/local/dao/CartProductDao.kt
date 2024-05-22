package com.example.mobilechallenge.cabifystore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mobilechallenge.cabifystore.data.local.model.CartProductEntity

@Dao
interface CartProductDao {

    @Query("SELECT * FROM cartProducts")
    fun getAllCartProducts(): List<CartProductEntity>

    @Insert
    fun addNewProduct(product: CartProductEntity): Long

    @Query("DELETE FROM cartProducts WHERE id = :id")
    fun deleteProductFromCart(id: Int): Int

}