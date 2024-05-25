package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class RemoveProductFromCartUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) : UseCase<RemoveProductFromCartUseCase.RequestValues, RemoveProductFromCartUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        return requestValues?.let { requestVal ->
            val addProductResource = productsRepository.addProductToCart(requestVal.products)
            return ResponseValue(addProductResource)
        } ?: ResponseValue(Resource.Error("Couldn't add the product to the cart"))

    }

    class RequestValues(val products: List<Product>) : UseCase.RequestValues
    class ResponseValue(val resource: Resource<Unit>) : UseCase.ResponseValue

}