package com.example.businessfinder.scenes.category

import androidx.lifecycle.viewModelScope
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.models.Category
import com.example.businessfinder.models.Result
import com.example.businessfinder.services.CategoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesViewModel(
    private val categoryService: CategoryService,
) : BaseViewModel() {

    val categories = MutableStateFlow<List<Category>>(emptyList())

    init {
        categoryService.getCategories().onEach {
            when (it) {
                is Result.Success -> onGetCategoriesSuccess(it.data)
                is Result.Failure -> failureResult(it.msg)
                is Result.Loading -> loadingResult()
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun onGetCategoriesSuccess(categories: List<Category>) {
        this.categories.value = categories
        successResult()
    }
}