package com.example.mobilechallenge.cabifystore.data.mappers

import com.example.mobilechallenge.cabifystore.data.remote.model.RemoteProduct
import com.example.mobilechallenge.cabifystore.domain.model.Product
import javax.inject.Inject

class ProductDataMapper @Inject constructor() {

    fun RemoteProduct.toProduct(): Product {
        return Product(
            code = code,
            name = name,
            price = price
        )
    }

}