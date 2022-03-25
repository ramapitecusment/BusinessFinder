package com.example.businessfinder.scenes.registration

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.Sphere
import com.example.businessfinder.models.User
import com.example.businessfinder.services.SphereService
import com.example.businessfinder.services.UserService
import kotlinx.coroutines.flow.*

class RegistrationViewModel(
    sphereService: SphereService,
    private val userService: UserService
) : BaseViewModel() {

    val binFlow = MutableStateFlow("")
    val emailFlow = MutableStateFlow("")
    val sphereNameFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")
    val companyNameFlow = MutableStateFlow("")
    val phoneNumberFlow = MutableStateFlow("")
    val passwordRepeatFlow = MutableStateFlow("")

    val spheres = MutableStateFlow<List<Sphere>>(emptyList())
    val spheresString = MutableStateFlow<List<String>>(emptyList())

    val navigateProfileScreenFlow = MutableSharedFlow<Unit>()

    init {
        sphereService.getAllSpheres().onEach {
            when (it) {
                is Result.Success -> {
                    spheres.value = it.data
                    successResult()
                }
                is Result.Failure -> failureResult(it.msg)
                is Result.Loading -> loadingResult()
            }
        }.launchIn(viewModelScope)

        spheres.onEach { spheres ->
            spheresString.value = spheres.map { it.name }
        }.launchIn(viewModelScope)
    }

    fun onSignUpClicked() {
        if (checkDataValid()) {
            val sphereId: String = spheres.value.firstOrNull { sphereNameFlow.value == it.name }?.id ?: return
            val user = User("", companyNameFlow.value, binFlow.value, sphereId, emailFlow.value, "")
            userService.createUserFlow(user, passwordFlow.value).onEach {
                when (it) {
                    is Result.Success -> onSignUpSuccess()
                    is Result.Failure -> failureResult(it.msg)
                    is Result.Loading -> loadingResult()
                }
            }.launchIn(viewModelScope)
        } else showToast("Проверьте все ли поля заполнены корректно")
    }

    private fun checkDataValid(): Boolean =
        binFlow.value.trim().isNotEmpty() &&
                binFlow.value.trim().length == 12 &&
                emailFlow.value.trim().isNotEmpty() &&
                sphereNameFlow.value.trim().isNotEmpty() &&
                passwordFlow.value.trim().isNotEmpty() &&
                companyNameFlow.value.trim().isNotEmpty() &&
                phoneNumberFlow.value.trim().isNotEmpty() &&
                passwordRepeatFlow.value.trim().isNotEmpty() &&
                passwordFlow.value == passwordRepeatFlow.value

    private fun onSignUpSuccess() {
        successResult()
        navigateProfileScreenFlow.safeEmit(Unit)
    }
}