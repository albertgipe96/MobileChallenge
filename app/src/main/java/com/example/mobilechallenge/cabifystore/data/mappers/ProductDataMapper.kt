package com.example.mobilechallenge.cabifystore.data.mappers

import com.example.mobilechallenge.cabifystore.data.local.model.CartProductEntity
import com.example.mobilechallenge.cabifystore.data.remote.model.RemoteProduct
import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.ProductCode
import javax.inject.Inject

class ProductDataMapper @Inject constructor() {

    fun RemoteProduct.toProduct(): Product {
        return Product(
            code = ProductCode.valueOf(code),
            name = name,
            price = price
        )
    }

    fun CartProductEntity.toCartProduct(): CartProduct {
        return CartProduct(
            id = id,
            code = ProductCode.valueOf(code),
            name = name,
            realPrice = price,
            discountedPrice = null
        )
    }

    fun Product.toCartProductEntity(): CartProductEntity {
        return CartProductEntity(
            code = code.name,
            name = name,
            price = price
        )
    }

}