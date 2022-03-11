package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.businessfinder.models.Result
import kotlinx.coroutines.tasks.await

class CategoryService {

    private val TAG = this::class.java.simpleName

    fun getCategories() = flow<Result<List<Category>>> {
        emit(Result.Loading)
        val data = FirebaseServices.categoriesCollection.get().await()
        emit(Result.Success(data.toObjects(Category::class.java)))
    }.catch {
        Log.e(TAG, "getCategories failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

}