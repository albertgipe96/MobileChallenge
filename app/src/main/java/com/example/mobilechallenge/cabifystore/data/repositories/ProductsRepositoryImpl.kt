package com.example.mobilechallenge.cabifystore.data.repositories

import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSource
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteProductDataSource: RemoteProductDataSource
) : ProductsRepository {

    override suspend fun getProducts(): Resource<List<Product>> { // Manage to get products from remote data source or local cache
        return remoteProductDataSource.getProducts()
    }

}