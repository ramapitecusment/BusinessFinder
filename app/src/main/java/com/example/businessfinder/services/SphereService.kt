package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.Sphere
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class SphereService {

    private val TAG = this::class.java.simpleName

    fun getSpheresByCategoryId(categoryId: String) = flow<Result<List<Sphere>>> {
        emit(Result.Loading)
        val data = FirebaseServices.spheresCollection.whereEqualTo("categoryId", categoryId).get().await()
        emit(Result.Success(data.toObjects(Sphere::class.java)))
    }.catch {
        Log.e(TAG, "getSpheresByCategoryId failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun getAllSpheres() = flow<Result<List<Sphere>>> {
        emit(Result.Loading)
        val data = FirebaseServices.spheresCollection.get().await()
        emit(Result.Success(data.toObjects(Sphere::class.java)))
    }.catch {
        Log.e(TAG, "getAllSpheres failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun getSphereById(sphereId: String) = flow<Result<Sphere>> {
        val data = FirebaseServices.spheresCollection.document(sphereId).get().await()
        emit(Result.Success(data.toObject(Sphere::class.java)!!))
    }.catch {
        Log.e(TAG, "getAllSpheres failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)


}