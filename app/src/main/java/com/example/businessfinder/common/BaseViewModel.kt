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
    val popBack = MutableSharedFlow<Unit>()

    val TAG = this.javaClass.name

    fun appContext() = this.getApplication<MainApplication>()

    protected fun showToast(message: String) {
        viewModelScope.launch { showToast.emit(message) }
    }

    private fun showLoading() {
        viewModelScope.launch { showLoading.emit(true) }
    }

    private fun hideLoading() {
        viewModelScope.launch { showLoading.emit(false) }
    }

    protected fun loadingResult() {
        showLoading()
    }

    protected fun failureResult(e: Throwable) {
        hideLoading()
        Log.d(TAG, e.toString())
        showToast(e.toString())
    }

    protected fun successResult() {
        hideLoading()
    }

    protected fun <T> MutableSharedFlow<T>.safeEmit(value: T) = viewModelScope.launch {
        this@safeEmit.emit(value)
    }
}