package com.example.businessfinder.models

import java.util.*

object MessageType {
    const val TEXT = "text"
    const val IMAGE = "image"
}

interface Message {
    val time: Date
    val senderId: String
    val type: String
}