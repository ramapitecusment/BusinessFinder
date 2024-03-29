package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.common.extensions.snapshotAsFlow
import com.example.businessfinder.models.User
import kotlinx.coroutines.tasks.await
import com.example.businessfinder.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class UserService {
    private val TAG: String = this::class.java.name

    fun createUserFlow(user: User, password: String): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val data = FirebaseServices.auth.createUserWithEmailAndPassword(user.email, password).await()
        Log.d(TAG, "createUser success data: $data, user: ${FirebaseServices.auth.currentUser}")
        user.firebaseUID = FirebaseServices.auth.currentUser!!.uid
        emit(Result.Success(Unit))
    }.flatMapLatest {
        insertUserFlow(user)
    }.catch {
        Log.e(TAG, "createUser failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun signInUserFlow(email: String, password: String): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val data = FirebaseServices.auth.signInWithEmailAndPassword(email, password).await()
        Log.d(TAG, "logInWithEmail success data: $data, user ${FirebaseServices.auth.currentUser}")
        emit(Result.Success(Unit))
    }.catch {
        Log.e(TAG, "signInUser failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    private fun insertUserFlow(user: User): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val documentId = FirebaseServices.auth.currentUser?.uid
        if (documentId == null) emit(Result.Failure(Throwable("User UID is null")))
        else {
            val data = FirebaseServices.currentUserDocument(documentId).set(user).await()
            Log.d(TAG, "insertUserData success: $data")
            emit(Result.Success(Unit))
        }
    }.catch {
        Log.e(TAG, "insertUser failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun updateUserFlow(user: User): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        val documentId = FirebaseServices.auth.currentUser?.uid
        if (documentId == null) emit(Result.Failure(Throwable("User UID is null")))
        else {
            val data = FirebaseServices.currentUserDocument(documentId).set(user).await()
            Log.d(TAG, "updateUser success: $data")
            emit(Result.Success(Unit))
        }
    }.catch {
        Log.e(TAG, "updateUser failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }.flowOn(Dispatchers.IO)

    fun getUser(documentId: String) = FirebaseServices.currentUserDocument(documentId).snapshotAsFlow()

    fun getUserFlow(documentId: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        val data = FirebaseServices.currentUserDocument(documentId).get().await()
        Log.d(TAG, "getUserFlow success: $data")
        emit(Result.Success(data.toObject(User::class.java)!!))
    }.catch {
        Log.e(TAG, "getUserFlow failure", it.fillInStackTrace())
        emit(Result.Failure(it))
    }

    fun getAllUsers() = FirebaseServices.usersCollection.snapshotAsFlow()

    fun signOut() = FirebaseServices.auth.signOut()


}