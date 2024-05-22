package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) : UseCase<GetCartProductsUseCase.RequestValues, GetCartProductsUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        val cartProductsResource = productsRepository.getCartProducts()
        return ResponseValue(cartProductsResource)
    }

    class RequestValues : UseCase.RequestValues
    class ResponseValue(val resource: Resource<List<CartProduct>>) : UseCase.ResponseValue

}