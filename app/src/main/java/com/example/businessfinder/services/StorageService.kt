package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StorageService {
    private val TAG: String = this::class.java.name

    fun uploadUserPhoto(imageBytes: ByteArray): Flow<Result<String>> = flow {
        emit(Result.Loading)
        val documentId = FirebaseServices.auth.currentUser?.uid
        if (documentId == null) emit(Result.Failure(Throwable("User UID is null")))
        else {
            val data = FirebaseServices.currentUserStorageRef
            Log.d(TAG, "updateUser success: $data")
            emit(Result.Success("Unit"))
        }
    }.catch {
        Log.e(TAG, "updateUser failure", it.fillInStackTrace())
        Result.Failure(it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)
//    "$KEY_USERS_PICTURE_REFERENCE/${UUID.nameUUIDFromBytes()}"
}