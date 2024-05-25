package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.ProductCode
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.repositories.OffersRepository
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class GetBestOfferUseCase @Inject constructor(
    private val offersRepository: OffersRepository
) : UseCase<GetBestOfferUseCase.RequestValues, GetBestOfferUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        return suspendCancellableCoroutine { continuation ->
            requestValues?.let { reqVal ->
                CoroutineScope(continuation.context).launch {
                    val result = offersRepository.getOffers()
                    var bestPrice = reqVal.product.price * reqVal.quantity
                    result
                        .onSuccess { offers ->
                            offers.filter { it.productCode == reqVal.product.code }.forEach { offer ->
                                offer.quantityToGetOneFree?.let { quantityToGetOneFree ->
                                    val calculatedPrice = reqVal.product.price * (reqVal.quantity / quantityToGetOneFree) + (reqVal.quantity % quantityToGetOneFree) * reqVal.product.price
                                    if (calculatedPrice < bestPrice) bestPrice = calculatedPrice
                                }
                                offer.discount?.let { discount ->
                                    if (reqVal.quantity >= discount.quantityOrMore) {
                                        val calculatedPrice = discount.discountedPrice * reqVal.quantity
                                        if (calculatedPrice < bestPrice) bestPrice = calculatedPrice
                                    }
                                }
                            }
                            continuation.resume(ResponseValue(bestPrice))
                        }
                        .onError {
                            continuation.resume(ResponseValue(bestPrice))
                        }
                }
            }
        }
    }

    class RequestValues(val product: Product, val quantity: Int) : UseCase.RequestValues
    class ResponseValue(val price: Double) : UseCase.ResponseValue

}