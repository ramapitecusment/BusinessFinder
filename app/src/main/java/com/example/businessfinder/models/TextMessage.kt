package com.example.businessfinder.models

data class TextMessage(
    val text: String,
    override val senderId: String,
    override val time: Long = System.currentTimeMillis(),
    override val type: String = MessageType.TEXT
) : Message {
    constructor() : this("", "", System.currentTimeMillis(), MessageType.TEXT)
}