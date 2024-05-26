package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.DomainMotherObject
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddProductToCartUseCaseTest {

    private lateinit var productsRepository: ProductsRepository
    private lateinit var products: List<Product>

    @Before
    fun setUp() {
        productsRepository = DomainMotherObject.fakeProductsRepository
        products = DomainMotherObject.fakeProducts
    }

    @Test
    fun `Add Products to cart, correctly stored to database`(): Unit = runBlocking {
        val result = productsRepository.addProductToCart(listOf())
        assertEquals(result, Resource.Success(Unit))
    }

}