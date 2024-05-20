package com.example.mobilechallenge.ui.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

enum class Shape(val shape: RoundedCornerShape) {
    small(RoundedCornerShape(4.dp)),
    medium(RoundedCornerShape(6.dp)),
    large(RoundedCornerShape(8.dp)),
}