package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.PurchasesRepository
import javax.inject.Inject

class GetPurchasesUseCase @Inject constructor(
    private val purchasesRepository: PurchasesRepository
) : UseCase<GetPurchasesUseCase.RequestValues, GetPurchasesUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        val purchasesResource = purchasesRepository.getPurchases()
        return ResponseValue(purchasesResource)
    }

    class RequestValues : UseCase.RequestValues
    class ResponseValue(val resource: Resource<List<Purchase>>) : UseCase.ResponseValue

}