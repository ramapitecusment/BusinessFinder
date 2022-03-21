package com.example.businessfinder.scenes.offers

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.*
import com.example.businessfinder.services.OfferService
import com.example.businessfinder.services.SphereService
import com.example.businessfinder.services.UserService
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll

class OffersViewModel(
    private val userService: UserService,
    private val offerService: OfferService,
    private val sphereService: SphereService,
) : BaseViewModel() {
    private var searchOffer: SearchOffer? = null

    val title = MutableStateFlow("")
    val offers = MutableStateFlow<List<OfferListItem>>(emptyList())

    fun start(searchOffer: SearchOffer?) {
        this.searchOffer = searchOffer
        getOffers()
    }

    private fun getOffers() {
        loadingResult()
        offersFlow().onEach { snapshot ->
            onGetOffersSuccess(snapshot.toObjects(Offer::class.java))
        }.launchIn(viewModelScope)
    }

    private fun offersFlow(): Flow<QuerySnapshot> =
        when {
            searchOffer?.isDirectSearch == true -> offerService.getOffersByDirectSearch(searchOffer!!)
            searchOffer?.sphereId != null -> offerService.getOffersBySphere(searchOffer?.sphereId!!)
            searchOffer?.ownerId != null -> offerService.getOffersByOwnerId(searchOffer?.ownerId!!)
            searchOffer?.acceptedUserId != null -> offerService.getOffersByAcceptedUserId(searchOffer?.acceptedUserId!!)
            else -> offerService.getAllOffers()
        }

    private suspend fun onGetOffersSuccess(offers: List<Offer>) {
        var isError = false
        val jobList = mutableListOf<Job>()
        val offerList = mutableListOf<OfferListItem>()
        offers.forEach { offer ->
            if (isError) return
            jobList.add(
                combine(
                    userService.getUserFlow(offer.ownerId),
                    sphereService.getSphereById(offer.sphereId)
                ) { user, sphere ->
                    if (user is Result.Success && sphere is Result.Success)
                        offerList.add(OfferListItem(offer, user.data, sphere.data))
                    else if (user is Result.Failure) {
                        failureResult(user.msg)
                        isError = true
                    } else if (sphere is Result.Failure) {
                        failureResult(sphere.msg)
                        isError = true
                    } else {
                    }
                }.launchIn(viewModelScope)
            )
        }
        jobList.joinAll()
        this.offers.value = offerList.toList()
        jobList.clear()
        successResult()
    }

}