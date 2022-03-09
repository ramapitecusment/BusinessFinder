package com.example.businessfinder.models

data class ChatChannel(val userIds: List<String>) {
    constructor() : this(listOf())
}