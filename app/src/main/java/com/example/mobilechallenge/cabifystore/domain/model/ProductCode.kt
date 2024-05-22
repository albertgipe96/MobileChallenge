package com.example.mobilechallenge.cabifystore.domain.model

import com.example.mobilechallenge.R

enum class ProductCode {
    VOUCHER, TSHIRT, MUG
}

fun ProductCode.getImageResByProductCode(): Int = when (this) {
    ProductCode.VOUCHER -> R.drawable.voucher
    ProductCode.TSHIRT -> R.drawable.tshirt
    ProductCode.MUG -> R.drawable.mug
}