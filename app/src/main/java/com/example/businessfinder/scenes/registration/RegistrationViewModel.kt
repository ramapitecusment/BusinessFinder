package com.example.businessfinder.scenes.registration

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.User
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val userService: UserService
) : BaseViewModel() {

    val binFlow = MutableStateFlow("")
    val emailFlow = MutableStateFlow("")
    val sphereIdFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")
    val companyNameFlow = MutableStateFlow("")
    val phoneNumberFlow = MutableStateFlow("")
    val passwordRepeatFlow = MutableStateFlow("")

    val navigateProfileScreenFlow = MutableSharedFlow<Unit>()

    fun onSignUpClicked() {
        if (checkDataValid()) {
            userService.createUserFlow(
                User("", companyNameFlow.value, binFlow.value, sphereIdFlow.value, emailFlow.value, ""),
                passwordFlow.value
            ).onEach {
                when (it) {
                    is Result.Success -> onSignUpSuccess()
                    is Result.Failure -> failureResult(it.msg)
                    is Result.Loading -> loadingResult()
                    else -> {}
                }
            }.launchIn(viewModelScope)
        } else {
            viewModelScope.launch {
                showToast.emit("Проверьте все ли поля заполнены корректно")
            }
        }

    }

    private fun checkDataValid(): Boolean =
        binFlow.value.trim().isNotEmpty() &&
                binFlow.value.trim().length == 12 &&
                emailFlow.value.trim().isNotEmpty() &&
                sphereIdFlow.value.trim().isNotEmpty() &&
                passwordFlow.value.trim().isNotEmpty() &&
                companyNameFlow.value.trim().isNotEmpty() &&
                phoneNumberFlow.value.trim().isNotEmpty() &&
                passwordRepeatFlow.value.trim().isNotEmpty() &&
                passwordFlow.value == passwordRepeatFlow.value

    private fun onSignUpSuccess() {
        successResult()
        viewModelScope.launch {
            navigateProfileScreenFlow.emit(Unit)
        }
    }
}