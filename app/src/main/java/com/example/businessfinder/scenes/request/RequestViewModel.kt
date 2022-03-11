package com.example.businessfinder.scenes.request

import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.Offer
import com.example.businessfinder.services.OfferService
import kotlinx.coroutines.flow.MutableSharedFlow

class RequestViewModel(
    private val offerService: OfferService
) : BaseViewModel() {

    val navigateProfileScreen = MutableSharedFlow<Unit>()
    val navigateOffersScreen = MutableSharedFlow<Offer>()

    fun onCreateOfferClicked() {

    }

    fun onSearchOfferClicked() {

    }
}