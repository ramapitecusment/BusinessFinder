package com.example.businessfinder.models

data class User(
    var firebaseUID: String,
    val companyName: String,
    val bin: String,
    val sphere: Int,
    val email: String,
    val photoUrl: String
) {
    constructor() : this("", "", "", -1, "", "")
}
