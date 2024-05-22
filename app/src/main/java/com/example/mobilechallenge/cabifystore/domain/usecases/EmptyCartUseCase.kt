package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class EmptyCartUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) : UseCase<EmptyCartUseCase.RequestValues, EmptyCartUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        val resource = productsRepository.emptyCart()
        return ResponseValue(resource)
    }

    class RequestValues : UseCase.RequestValues
    class ResponseValue(val resource: Resource<Unit>) : UseCase.ResponseValue

}