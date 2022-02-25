package com.example.businessfinder.common

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessfinder.MainApplication
import com.example.businessfinder.models.AlertDialogModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : AndroidViewModel(MainApplication.instance) {
    val showToast = MutableSharedFlow<String>()
    val showAlertDialog = MutableSharedFlow<AlertDialogModel>()

    protected val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun appContext() = this.getApplication<MainApplication>()

    protected fun showToast(message: String) {
        viewModelScope.launch {
            showToast.tryEmit(message)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}