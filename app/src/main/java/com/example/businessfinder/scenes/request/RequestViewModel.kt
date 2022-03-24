package com.example.businessfinder.scenes.request

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.*
import com.example.businessfinder.services.FirebaseServices
import com.example.businessfinder.services.OfferService
import com.example.businessfinder.services.SphereService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RequestViewModel(
    sphereService: SphereService,
    private val offerService: OfferService
) : BaseViewModel() {

    val spheres = MutableStateFlow<List<Sphere>>(emptyList())
    val spheresString = MutableStateFlow<List<String>>(emptyList())

    val priceFlow = MutableStateFlow("")
    val deadlineFlow = MutableStateFlow("")
    val sphereNameFlow = MutableStateFlow("")
    val descriptionFlow = MutableStateFlow("")

    val navigateOffersScreen = MutableSharedFlow<SearchOffer>()

    init {
        sphereService.getAllSpheres().onEach(::onGetSpheresResult).launchIn(viewModelScope)

        spheres.onEach { spheres ->
            spheresString.value = spheres.map { it.name }
        }.launchIn(viewModelScope)
    }

    fun onCreateOfferClicked() {
        if (checkDataValid()) {
            val sphereId: String = spheres.value.firstOrNull { sphereNameFlow.value == it.name }?.id ?: return
            val offer = getCreatedOffer(sphereId)
            offerService.createOffer(offer).onEach(::onCreateOfferResult).launchIn(viewModelScope)
        } else showToast("Проверьте все ли поля заполнены корректно")
    }

    fun onSearchOfferClicked() {
        val searchOffer = SearchOffer(isDirectSearch = true)
        if (priceFlow.value.isNotEmpty()) searchOffer.price = priceFlow.value.toInt()
        if (deadlineFlow.value.isNotEmpty()) searchOffer.dayDeadline = deadlineFlow.value.toInt()
        if (descriptionFlow.value.isNotEmpty()) searchOffer.description = descriptionFlow.value
        if (sphereNameFlow.value.isNotEmpty())
            searchOffer.sphereId = spheres.value.firstOrNull { sphereNameFlow.value == it.name }?.id
        navigateOffersScreen.safeEmit(searchOffer)
    }

    private fun onGetSpheresResult(spheresResult: Result<List<Sphere>>) {
        when (spheresResult) {
            is Result.Success -> onGetSpheresSuccess(spheresResult.data)
            is Result.Failure -> failureResult(spheresResult.msg)
            is Result.Loading -> loadingResult()
            else -> {}
        }
    }

    private fun onGetSpheresSuccess(spheres: List<Sphere>) {
        this.spheres.value = spheres
        successResult()
    }

    private fun onCreateOfferResult(result: Result<Unit>) {
        when (result) {
            is Result.Success -> onCreateOfferSuccess()
            is Result.Failure -> failureResult(result.msg)
            is Result.Loading -> loadingResult()
            else -> {}
        }
    }

    private fun onCreateOfferSuccess() {
        successResult()
        navigateOffersScreen.safeEmit(SearchOffer(ownerId = FirebaseServices.auth.uid!!))
    }

    private fun checkDataValid(): Boolean =
        priceFlow.value.trim().isNotEmpty() &&
                sphereNameFlow.value.trim().isNotEmpty() &&
                descriptionFlow.value.trim().isNotEmpty() &&
                deadlineFlow.value.trim().isNotEmpty()

    private fun getCreatedOffer(sphereId: String) = Offer(
        "",
        priceFlow.value.toInt(),
        sphereId,
        FirebaseServices.auth.uid!!,
        emptyList(),
        deadlineFlow.value.toInt(),
        descriptionFlow.value
    )

}