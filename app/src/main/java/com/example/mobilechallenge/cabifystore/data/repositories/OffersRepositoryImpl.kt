package com.example.mobilechallenge.cabifystore.data.repositories

import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteConfigProvider
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteConfigProviderImpl
import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.OffersRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(
    private val remoteConfigProvider: RemoteConfigProvider
) : OffersRepository {

    companion object {
        const val OFFERS_KEY = "offers"
    }

    override suspend fun getOffers(): Resource<List<Offer>> {
        val jsonString = remoteConfigProvider.getString(OFFERS_KEY)
        return if (jsonString.isNotEmpty()) {
            val offerData = Gson().fromJson(jsonString, OfferData::class.java)
            Resource.Success(offerData.offers)
        } else Resource.Error("No active offers")

    }

    private data class OfferData(
        val offers: List<Offer>
    )


}