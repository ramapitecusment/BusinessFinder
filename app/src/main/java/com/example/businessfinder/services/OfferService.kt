package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.common.extensions.snapshotAsFlow
import com.example.businessfinder.models.Offer
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.SearchOffer
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class OfferService {

    private val TAG = this::class.java.simpleName

    fun getOffersByPriceAndSphere(searchOffer: SearchOffer): Flow<QuerySnapshot> {
        var offersCollection: Query = FirebaseServices.offersCollection
        if (searchOffer.price != null)
            offersCollection = offersCollection.whereLessThanOrEqualTo("price", searchOffer.price!!)
        if (searchOffer.sphereId != null)
            offersCollection = offersCollection.whereEqualTo("sphereId", searchOffer.sphereId)
        offersCollection = offersCollection.orderBy("price")
        return offersCollection.snapshotAsFlow()
    }

    fun getOffersBySphere(sphereId: String) = FirebaseServices.offersCollection
        .whereEqualTo("sphereId", sphereId)
        .orderBy("id")
        .snapshotAsFlow()

    fun getOffersByOwnerId(ownerId: String) = FirebaseServices.offersCollection
        .whereEqualTo("ownerId", ownerId)
        .orderBy("id")
        .snapshotAsFlow()

    fun getOffersByAcceptedUserId(acceptedUserId: String) = FirebaseServices.offersCollection
        .whereArrayContains("acceptedUserIds", acceptedUserId)
        .orderBy("id")
        .snapshotAsFlow()

    fun getAllOffers() = FirebaseServices.offersCollection
        .orderBy("id")
        .snapshotAsFlow()

    fun createOffer(offer: Offer) = flow {
        emit(Result.Loading)
        val offerDocument = FirebaseServices.offersCollection.document()
        offer.id = offerDocument.id
        val data = offerDocument.set(offer).await()
        Log.d(TAG, "createOffer data: $data")
        emit(Result.Success(Unit))
    }.catch {
        Log.e(TAG, "createOffer failure", it.fillInStackTrace())
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