package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.OffersRepository
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val offersRepository: OffersRepository
) : UseCase<GetOffersUseCase.RequestValues, GetOffersUseCase.ResponseValue>() {

    override suspend fun invoke(requestValues: RequestValues?): ResponseValue {
        val offersResource = offersRepository.getOffers()
        return ResponseValue(offersResource)
    }

    class RequestValues : UseCase.RequestValues
    class ResponseValue(val resource: Resource<List<Offer>>) : UseCase.ResponseValue

}