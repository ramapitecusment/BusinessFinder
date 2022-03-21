package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.models.Offer
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.User
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

    fun getOffersByOwnerId(ownerId: String) = flow<Result<List<Offer>>> {
        emit(Result.Loading)
        val data = FirebaseServices.offersCollection.whereEqualTo("ownerId", ownerId).get().await()
        emit(Result.Success(data.toObjects(Offer::class.java)))
    }.catch {
        Log.e(TAG, "getOffersBySphere failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun getOffersByAcceptedUserId(acceptedUserId: String) = flow<Result<List<Offer>>> {
        emit(Result.Loading)
        val data = FirebaseServices.offersCollection.whereArrayContains("acceptedUserIds", acceptedUserId).get().await()
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

    fun addAcceptedOfferToUser(offer: Offer) = flow {
        emit(Result.Loading)
        val _offer = offer.copy()
        val acceptedUserIds = offer.acceptedUserIds.toMutableList()
        val userId = FirebaseServices.auth.uid!!
        acceptedUserIds.add(userId)
        _offer.acceptedUserIds = acceptedUserIds
        val offerData = FirebaseServices.offersCollection.document(_offer.id).set(_offer).await()
        Log.d(TAG, "offerData: $offerData -- $_offer")
        emit(Result.Success(Unit))
    }.catch {
        Log.e(TAG, "addAcceptedOfferToUser failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun deleteDeclinedOfferFromUser(offer: Offer) = flow {
        emit(Result.Loading)
        val _offer = offer.copy()
        val acceptedUserIds = offer.acceptedUserIds.toMutableList()
        val userId = FirebaseServices.auth.uid!!
        acceptedUserIds.remove(userId)
        _offer.acceptedUserIds = acceptedUserIds
        val offerData = FirebaseServices.offersCollection.document(_offer.id).set(_offer).await()
        Log.d(TAG, "offerData: $offerData -- $_offer")
        emit(Result.Success(Unit))
    }.catch {
        Log.e(TAG, "deleteDeclinedOfferFromUser failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

}