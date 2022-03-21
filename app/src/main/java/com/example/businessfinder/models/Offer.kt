package com.example.businessfinder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    var id: String,
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
    var price: Int? = null,
    var sphereId: String? = null,
    val ownerId: String? = null,
    val acceptedUserId: String? = null,
    var dayDeadline: Int? = null,
    var description: String? = null,
    val isDirectSearch: Boolean = false
) : Parcelable
