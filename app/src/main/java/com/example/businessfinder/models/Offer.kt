package com.example.businessfinder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val id: String,
    val price: Int,
    val sphereId: String,
    val ownerId: String,
    var acceptedUserIds: List<String>,
    val dayDeadline: Int,
    val description: String
) : Parcelable {
    constructor() : this("", 0, "", "", emptyList(), 0, "")
}

@Parcelize
data class OfferListItem(
    val offer: Offer,
    val user: User,
    val sphere: Sphere
) : Parcelable

@Parcelize
data class SearchOffer(
    val price: Int? = null,
    val sphereId: String? = null,
    val ownerId: String? = null,
    val acceptedUserId: String? = null,
    val dayDeadline: Int? = null,
    val description: String? = null
) : Parcelable
