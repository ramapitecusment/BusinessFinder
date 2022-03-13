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
import kotlin.coroutines.cancellation.CancellationException

class OffersViewModel(
    private val userService: UserService,
    private val offerService: OfferService,
    private val sphereService: SphereService,
) : BaseViewModel() {
    var sphereId: String? = null

    val title = MutableStateFlow("")
    val offers = MutableStateFlow<List<OfferListItem>>(emptyList())

    fun start(searchOffer: SearchOffer?) {
        this.sphereId = searchOffer?.sphereId
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
        if (sphereId != null) offerService.getOffersBySphere(sphereId!!)
        else offerService.getAllOffers()

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
//        offerList.clear()
    }

}