package com.example.businessfinder.models

data class User(
    var firebaseUID: String,
    val companyName: String,
    val bin: String,
    val sphere: Int,
    val email: String,
    var photoUrl: String
) {
    constructor() : this("", "", "", -1, "", "")
}
