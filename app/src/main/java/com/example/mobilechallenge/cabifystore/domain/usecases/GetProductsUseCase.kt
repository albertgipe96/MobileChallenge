package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) : UseCase<GetProductsUseCase.RequestValues, GetProductsUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        val productsResource = productsRepository.getProducts()
        return ResponseValue(productsResource)
    }

    class RequestValues : UseCase.RequestValues
    class ResponseValue(val resource: Resource<List<Product>>) : UseCase.ResponseValue

}