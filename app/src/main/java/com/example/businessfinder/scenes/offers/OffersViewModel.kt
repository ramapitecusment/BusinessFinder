package com.example.businessfinder.scenes.offers

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.*
import com.example.businessfinder.services.OfferService
import com.example.businessfinder.services.SphereService
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll

class OffersViewModel(
    private val userService: UserService,
    private val offerService: OfferService,
    private val sphereService: SphereService,
) : BaseViewModel() {
    var sphereId: String? = null
    var ownerId: String? = null
    var acceptedUserId: String? = null

    val title = MutableStateFlow("")
    val offers = MutableStateFlow<List<OfferListItem>>(emptyList())

    fun start(searchOffer: SearchOffer?) {
        this.sphereId = searchOffer?.sphereId
        this.ownerId = searchOffer?.ownerId
        this.acceptedUserId = searchOffer?.acceptedUserId
        getOffers()
    }


    private fun getOffers() {
        offersFlow().onEach { offersResult ->
            when (offersResult) {
                is Result.Success -> onGetOffersSuccess(offersResult.data)
                is Result.Failure -> failureResult(offersResult.msg)
                is Result.Loading -> loadingResult()
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun offersFlow(): Flow<Result<List<Offer>>> =
        when {
            sphereId != null -> offerService.getOffersBySphere(sphereId!!)
            ownerId != null -> offerService.getOffersByOwnerId(ownerId!!)
            acceptedUserId != null -> offerService.getOffersByAcceptedUserId(acceptedUserId!!)
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