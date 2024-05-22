package com.example.mobilechallenge.cabifystore.domain.model

import java.time.LocalDateTime

data class Purchase(
    val amountSpent: Double,
    val dateInMillis: Long
)