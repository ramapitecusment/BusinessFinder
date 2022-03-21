package com.example.businessfinder.scenes.offer

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.OfferListItem
import com.example.businessfinder.models.Result
import com.example.businessfinder.services.OfferService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OfferViewModel(
    private val offerService: OfferService
) : BaseViewModel() {

    private var offerListItem: OfferListItem? = null

    fun init(offer: OfferListItem) {
        offerListItem = offer
    }

    fun onAcceptClicked() {
        offerService.addAcceptedOfferToUser(offerListItem!!.offer).onEach {
            when (it) {
                is Result.Success -> onEditSuccess("Добавлено")
                is Result.Failure -> failureResult(it.msg)
                is Result.Loading -> loadingResult()
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun onDeclineClicked() {
        offerService.deleteDeclinedOfferFromUser(offerListItem!!.offer).onEach {
            when (it) {
                is Result.Success -> onEditSuccess("Удалено")
                is Result.Failure -> failureResult(it.msg)
                is Result.Loading -> loadingResult()
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun onEditSuccess(text: String) {
        showToast(text)
        popBack.safeEmit(Unit)
    }

}