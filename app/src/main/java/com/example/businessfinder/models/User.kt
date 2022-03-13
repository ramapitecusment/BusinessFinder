package com.example.businessfinder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var firebaseUID: String,
    val companyName: String,
    val bin: String,
    val sphere: String,
    val email: String,
    var photoUrl: String
) : Parcelable {
    constructor() : this("", "", "", "", "", "")
}
