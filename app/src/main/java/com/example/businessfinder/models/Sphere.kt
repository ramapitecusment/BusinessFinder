package com.example.businessfinder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sphere(
    val id: String,
    val name: String,
    val categoryId: String
) : Parcelable {
    constructor() : this("", "", "")
}
