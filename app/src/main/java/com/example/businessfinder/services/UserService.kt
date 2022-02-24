package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.common.Constants.KEY_USERS_COLLECTION
import com.example.businessfinder.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.businessfinder.models.Result
import java.lang.NullPointerException

class UserService(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    private val TAG: String = this::class.java.name
    var user: FirebaseUser? = auth.currentUser
    val currentUserDocRef = db.document("users/${auth.currentUser?.uid ?: throw NullPointerException("User UID is null")}")

    // https://betterprogramming.pub/how-to-use-kotlin-coroutines-with-firebase-6f8577a3e00f
    suspend fun createUser(user: User): Result<Unit> =
        try {
            auth.createUserWithEmailAndPassword(user.email, user.password).await()
            Log.d(TAG, "createUser success: ${this.user}")
            insertUserData(user)
            Result.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "createUser failure", e);
            Result.Failure(e)
        }

    suspend fun signInUser(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d(TAG, "logInWithEmail success: ${this.user?.uid}")
            Result.Success(Unit)
        } catch (e: Exception) {
            Log.d(TAG, "logInWithEmail exception: ${e.message}")
            Result.Failure(e)
        }
    }


    suspend fun insertUserData(user: User) {
        try {
            val data = db.collection(KEY_USERS_COLLECTION).document(user.id).set(user).await()
            Log.d(TAG, "insertUserData success: $data")
        } catch (e: Exception) {
            Log.d(TAG, "insertUserData exception: $e")
        }

    }

}