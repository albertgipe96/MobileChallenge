package com.example.mobilechallenge.cabifystore.data.remote.dataSource

import com.example.mobilechallenge.cabifystore.data.mappers.ProductDataMapper
import com.example.mobilechallenge.cabifystore.data.remote.ProductsApi
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.common.data.networkResult.onError
import com.example.mobilechallenge.common.data.networkResult.onException
import com.example.mobilechallenge.common.data.networkResult.onSuccess
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

interface RemoteProductDataSource {
    suspend fun getProducts(): Resource<List<Product>>
}

class RemoteProductDataSourceImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val productDataMapper: ProductDataMapper
) : RemoteProductDataSource {

    override suspend fun getProducts(): Resource<List<Product>> = with(productDataMapper) {
        val result = productsApi.getProducts()
        return suspendCancellableCoroutine { continuation ->
            result
                .onSuccess { remoteProductData ->
                    val products = remoteProductData.products.map { it.toProduct() }
                    continuation.resume(Resource.Success(products))
                }
                .onError { code, message ->
                    continuation.resume(Resource.Error(message))
                }
                .onException { t->
                    continuation.resume(Resource.Exception(t))
                }
        }
    }
}