package com.example.businessfinder.common.extensions

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

fun LocalDateTime.toFormat(format: String): String = this.format(DateTimeFormatter.ofPattern(format))