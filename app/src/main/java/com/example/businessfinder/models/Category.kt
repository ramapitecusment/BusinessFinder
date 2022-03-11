package com.example.businessfinder.models

data class Category(
    val id: String,
    val name: String,
    val photoUrl: String
) {
    constructor() : this("", "", "")
}