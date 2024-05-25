package com.example.mobilechallenge.common.presentation.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun getDateString(dateInMillis: Long): String {
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateInMillis), ZoneId.systemDefault())
        return formatter.format(date)
    }

}