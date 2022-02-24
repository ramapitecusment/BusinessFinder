package com.example.businessfinder.common

import androidx.lifecycle.AndroidViewModel
import com.example.businessfinder.MainApplication
import com.example.businessfinder.models.AlertDialogModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : AndroidViewModel(MainApplication.instance) {
    val showToast = MutableSharedFlow<String>()
    val showAlertDialog = MutableSharedFlow<AlertDialogModel>()

    protected val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}