package com.example.businessfinder.common

import androidx.lifecycle.AndroidViewModel
import com.example.businessfinder.MainApplication
import com.example.businessfinder.models.AlertDialogModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : AndroidViewModel(MainApplication.instance) {
    val showToast = MutableSharedFlow<String>()
    val showAlertDialog = MutableSharedFlow<AlertDialogModel>()

}