package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Product
import javax.inject.Inject

class ComputeTotalPriceUseCase @Inject constructor(
    private val getBestOfferUseCase: GetBestOfferUseCase
) : UseCase<ComputeTotalPriceUseCase.RequestValues, ComputeTotalPriceUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        var totalPrice = 0.0
        requestValues?.let { requestVal ->
            requestVal.cartProducts.groupBy { it.code }.forEach { map ->
                val cartProduct = map.value.first()
                val quantity = map.value.size
                val bestPrice = getBestOfferUseCase(GetBestOfferUseCase.RequestValues(cartProduct.toProduct(), quantity)).price
                totalPrice += bestPrice
            }
        }
        return ResponseValue(totalPrice)
    }

    class RequestValues(val cartProducts: List<CartProduct>) : UseCase.RequestValues
    class ResponseValue(val totalPrice: Double) : UseCase.ResponseValue


    private fun CartProduct.toProduct(): Product {
        return Product(code, name, price)
    }

}