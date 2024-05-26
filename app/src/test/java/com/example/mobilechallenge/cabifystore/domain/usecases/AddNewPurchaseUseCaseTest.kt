package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.DomainMotherObject
import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.PurchasesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddNewPurchaseUseCaseTest {

    private lateinit var purchasesRepository: PurchasesRepository

    @Before
    fun setUp() {
        purchasesRepository = DomainMotherObject.fakePurchasesRepository
    }

    @Test
    fun `Add New Purchase, correctly stored to database`() = runBlocking {
        val purchase = Purchase(100.0, 1111)
        val result = purchasesRepository.addPurchase(purchase)
        assertEquals(result is Resource.Success, true)
    }

}