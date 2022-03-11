package com.example.businessfinder.scenes.offers

import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.OfferListItem
import com.example.businessfinder.models.SearchOffer
import com.example.businessfinder.services.OfferService
import com.example.businessfinder.services.SphereService
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow

class OffersViewModel(
    private val userService: UserService,
    private val offerService: OfferService,
    private val sphereService: SphereService,
) : BaseViewModel() {
    var sphereId: String? = null

    val title = MutableStateFlow("")
    val offers = MutableStateFlow<List<OfferListItem>>(emptyList())

    init {
        offers.value = emptyList()
    }

    fun start(searchOffer: SearchOffer?) {
        if (this.sphereId != null) return
        this.sphereId = searchOffer?.sphereId
    }
}