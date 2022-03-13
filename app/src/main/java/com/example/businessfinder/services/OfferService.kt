package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.models.Offer
import com.example.businessfinder.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class OfferService {

    private val TAG = this::class.java.simpleName

    fun getOffersBySphere(sphereId: String) = flow<Result<List<Offer>>> {
        emit(Result.Loading)
        val data = FirebaseServices.offersCollection.whereEqualTo("sphereId", sphereId).get().await()
        emit(Result.Success(data.toObjects(Offer::class.java)))
    }.catch {
        Log.e(TAG, "getOffersBySphere failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun getAllOffers() = flow<Result<List<Offer>>> {
        emit(Result.Loading)
        val data = FirebaseServices.offersCollection.get().await()
        emit(Result.Success(data.toObjects(Offer::class.java)))
    }.catch {
        Log.e(TAG, "getAllOffers failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

}