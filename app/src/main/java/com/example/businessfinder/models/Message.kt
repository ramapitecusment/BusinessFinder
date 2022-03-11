package com.example.businessfinder.models

object MessageType {
    const val TEXT = "text"
    const val IMAGE = "image"
}

interface Message {
    val time: Long
    val senderId: String
    val type: String
}