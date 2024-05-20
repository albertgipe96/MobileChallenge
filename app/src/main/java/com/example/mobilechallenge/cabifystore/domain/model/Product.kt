package com.example.mobilechallenge.cabifystore.domain.model

import com.example.mobilechallenge.R

enum class ProductCode {
    VOUCHER, TSHIRT, MUG
}

data class Product(
    val code: ProductCode,
    val name: String,
    val price: Double
)

fun Product.getImageResByProduct(): Int = when (code) {
    ProductCode.VOUCHER -> R.drawable.voucher
    ProductCode.TSHIRT -> R.drawable.tshirt
    ProductCode.MUG -> R.drawable.mug
}
