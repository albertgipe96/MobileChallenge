package com.example.mobilechallenge.cabifystore.data.remote.dataSource

import com.example.mobilechallenge.cabifystore.data.remote.ProductsApi
import com.example.mobilechallenge.common.data.networkResult.onError
import com.example.mobilechallenge.common.data.networkResult.onException
import com.example.mobilechallenge.common.data.networkResult.onSuccess
import timber.log.Timber
import javax.inject.Inject

interface RemoteProductDataSource {
    suspend fun getProducts()
}

class RemoteProductDataSourceImpl @Inject constructor(
    private val productsApi: ProductsApi
) : RemoteProductDataSource {

    override suspend fun getProducts() {
        productsApi.getProducts()
            .onSuccess { Timber.d("Products fetched success: ${it.products}") }
            .onError { code, message -> Timber.e("Products fetch error: ${message}") }
            .onException { Timber.e("Exception: ${it.message}") }
    }
}