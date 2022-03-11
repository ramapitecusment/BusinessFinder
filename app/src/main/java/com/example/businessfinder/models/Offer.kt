package com.example.businessfinder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Offer(
    val price: Int,
    val sphereId: String,
    val ownerId: String,
    val dayDeadline: Int,
    val description: String
) {
    constructor() : this(0, "", "", 0, "")
}

data class OfferListItem(
    val id: String,
    val offer: Offer,
    val user: User,
    val sphere: Sphere
)

@Parcelize
data class SearchOffer(
    val price: Int? = null,
    val sphereId: String? = null,
    val ownerId: String? = null,
    val dayDeadline: Int? = null,
    val description: String? = null
) : Parcelable
