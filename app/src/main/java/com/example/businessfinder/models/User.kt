package com.example.businessfinder.models

data class User(
    val id: String,
    val companyName: String,
    val bin: String,
    val sphere: Int,
    val email: String,
    val password: String
)
