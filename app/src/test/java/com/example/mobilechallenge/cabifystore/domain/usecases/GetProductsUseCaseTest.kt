package com.example.mobilechallenge.cabifystore.domain.usecases

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.mobilechallenge.cabifystore.data.local.database.AppDatabase
import com.example.mobilechallenge.cabifystore.domain.DomainMotherObject
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class GetProductsUseCaseTest {

    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setUp() {
        productsRepository = DomainMotherObject.fakeProductsRepository
    }

    @Test
    fun `Get Products from remote, correct product list return`(): Unit = runBlocking {
        val products = productsRepository.getProducts()
        products
            .onSuccess {
                assertEquals(it.isNotEmpty(), true)
            }
    }

}