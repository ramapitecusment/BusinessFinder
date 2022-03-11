package com.example.businessfinder.models

data class Sphere(
    val id: String,
    val name: String,
    val categoryId: String
) {
    constructor() : this("", "", "")
}
