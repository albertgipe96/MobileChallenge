package com.example.mobilechallenge.cabifystore.data.local.dataSource

import com.example.mobilechallenge.cabifystore.data.local.database.AppDatabase
import com.example.mobilechallenge.cabifystore.data.mappers.ProductDataMapper
import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import javax.inject.Inject
import javax.inject.Provider

interface LocalProductDataSource {
    suspend fun getCartProducts(): Resource<List<CartProduct>>
    suspend fun addProductToCart(product: Product): Resource<Unit>
}

class LocalProductDataSourceImpl @Inject constructor(
    private val appDatabaseProvider: Provider<AppDatabase>,
    private val productDataMapper: ProductDataMapper
) : LocalProductDataSource {

    private val appDatabase by lazy { appDatabaseProvider.get() }

    private val cartProductDao by lazy { appDatabase.cartProductDao() }

    override suspend fun getCartProducts(): Resource<List<CartProduct>> = with(productDataMapper) {
        val result = cartProductDao.getAllCartProducts()
        val products = result.map { it.toCartProduct() }
        return Resource.Success(products)
    }

    override suspend fun addProductToCart(product: Product): Resource<Unit> = with(productDataMapper) {
        val result = cartProductDao.addNewProduct(product.toCartProductEntity())
        return if (result != -1L) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Couldn't insert to database")
        }
    }
}