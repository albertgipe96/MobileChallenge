package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class RemoveProductFromCartUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) : UseCase<RemoveProductFromCartUseCase.RequestValues, RemoveProductFromCartUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        return requestValues?.let { requestVal ->
            val removeProductResource = productsRepository.removeProductFromCart(requestVal.cartProduct)
            return ResponseValue(removeProductResource)
        } ?: ResponseValue(Resource.Error("Couldn't remove the product from the cart"))

    }

    class RequestValues(val cartProduct: CartProduct) : UseCase.RequestValues
    class ResponseValue(val resource: Resource<Unit>) : UseCase.ResponseValue

}