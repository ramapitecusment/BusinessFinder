package com.example.businessfinder.scenes.sphere

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.Sphere
import com.example.businessfinder.services.SphereService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SphereViewModel(
    private val sphereService: SphereService
) : BaseViewModel() {

    var categoryId: String? = null

    val title = MutableStateFlow("")
    val spheres = MutableStateFlow<List<Sphere>>(emptyList())

    init {
        spheres.value = emptyList()
    }

    fun start(categoryId: String, categoryName: String) {
        this.categoryId = categoryId
        title.value = categoryName
        getSpheres()
    }

    private fun getSpheres() {
        val spheresFlow = if (categoryId != null) sphereService.getSpheresByCategoryId(categoryId!!)
        else sphereService.getAllSpheres()
        spheresFlow.onEach {
            when (it) {
                is Result.Success -> onGetSpheresSuccess(it.data)
                is Result.Failure -> failureResult(it.msg)
                is Result.Loading -> loadingResult()
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun onGetSpheresSuccess(data: List<Sphere>) {
        spheres.value = data
        successResult()
    }
}