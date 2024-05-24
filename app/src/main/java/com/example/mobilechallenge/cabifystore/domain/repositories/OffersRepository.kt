package com.example.mobilechallenge.cabifystore.domain.repositories

import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.example.mobilechallenge.cabifystore.domain.model.Resource

interface OffersRepository {
    suspend fun getOffers(): Resource<List<Offer>>
}