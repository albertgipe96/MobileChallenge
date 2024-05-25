package com.example.mobilechallenge.cabifystore.domain.model

data class Offer(
    val productCode: ProductCode,
    val quantityToGetOneFree: Int?,
    val discount: QuantityDiscount?
)

data class QuantityDiscount(
    val quantityOrMore: Int,
    val discountedPrice: Double
)