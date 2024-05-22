package com.example.mobilechallenge.cabifystore.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobilechallenge.cabifystore.data.local.dao.CartProductDao
import com.example.mobilechallenge.cabifystore.data.local.model.CartProductEntity

@Database(entities = [CartProductEntity::class], exportSchema = true, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartProductDao(): CartProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}