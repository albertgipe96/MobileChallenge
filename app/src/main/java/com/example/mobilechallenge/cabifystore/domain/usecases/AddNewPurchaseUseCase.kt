package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.PurchasesRepository
import javax.inject.Inject

class AddNewPurchaseUseCase @Inject constructor(
    private val purchasesRepository: PurchasesRepository
) : UseCase<AddNewPurchaseUseCase.RequestValues, AddNewPurchaseUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        return requestValues?.let { requestVal ->
            val purchase = Purchase(amountSpent = requestVal.totalPrice, dateInMillis = System.currentTimeMillis())
            val addProductResource = purchasesRepository.addPurchase(purchase)
            return ResponseValue(addProductResource)
        } ?: ResponseValue(Resource.Error("Couldn't save the purchase"))

    }

    class RequestValues(val totalPrice: Double) : UseCase.RequestValues
    class ResponseValue(val resource: Resource<Unit>) : UseCase.ResponseValue

}