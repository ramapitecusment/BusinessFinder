package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.common.Constants.KEY_USERS_PICTURE_REFERENCE
import com.example.businessfinder.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.*

class StorageService {
    private val TAG: String = this::class.java.name

    fun uploadUserPhoto(imageBytes: ByteArray): Flow<Result<String>> = flow {
        emit(Result.Loading)
        val documentId = FirebaseServices.auth.currentUser?.uid
        if (documentId == null) {
            emit(Result.Failure(Throwable("User UID is null")))
            return@flow
        }
        val data = FirebaseServices.currentUserStorageRef(documentId)
            .child("$KEY_USERS_PICTURE_REFERENCE/${UUID.nameUUIDFromBytes(imageBytes)}")
        data.putBytes(imageBytes).await()
        Log.d(TAG, "uploadUserPhoto success: $data")
        emit(Result.Success(data.path))
    }.catch {
        Log.e(TAG, "uploadUserPhoto failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

}