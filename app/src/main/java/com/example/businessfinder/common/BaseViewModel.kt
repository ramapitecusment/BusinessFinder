package com.example.businessfinder.common

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessfinder.MainApplication
import com.example.businessfinder.models.AlertDialogModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : AndroidViewModel(MainApplication.instance) {
    val showToast = MutableSharedFlow<String>()
    val showAlertDialog = MutableSharedFlow<AlertDialogModel>()
    val showLoading = MutableSharedFlow<Boolean>()

    val TAG = this.javaClass.name

    fun appContext() = this.getApplication<MainApplication>()

    protected fun showToast(message: String) {
        viewModelScope.launch { showToast.emit(message) }
    }

    protected fun showLoading() {
        viewModelScope.launch { showLoading.emit(true) }
    }

    protected fun hideLoading() {
        viewModelScope.launch { showLoading.emit(false) }
    }

    protected fun loadingResult() {
        showLoading()
    }

    protected fun failureResult(e: Throwable) {
        hideLoading()
        Log.d(TAG, e.toString())
    }

    protected fun successResult() {
        hideLoading()
    }
}