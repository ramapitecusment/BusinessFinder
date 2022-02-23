package com.example.businessfinder.services

import android.util.Log
import com.example.businessfinder.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserService {

    private val TAG: String = this::class.java.name
    private var auth = FirebaseAuth.getInstance()
    var user: FirebaseUser? = auth.currentUser

    suspend fun createUser(user: User): Result<String> = withContext(Dispatchers.IO) {
        return@withContext suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    this@UserService.user = auth.currentUser
                    updateUser(user)
                    cont.resume(Result.success("Success"))
                } else {
                    Log.e(TAG, "createUserWithEmail:failure", task.exception);
                    cont.resume(Result.failure(task.exception ?: Throwable("${task.result}")))
                }
            }
        }
    }


    suspend fun signInUser(email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        return@withContext suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    user = auth.currentUser
                    cont.resume(Result.success("Success"))
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    cont.resume(Result.failure(task.exception ?: Throwable("${task.result}")))
                }
            }
        }
    }

    suspend fun updateUser(user: User) {
        this.user?.updateProfile(userData(user))?.addOnCompleteListener {

        }
    }

    private fun userData(user: User) = UserProfileChangeRequest.Builder().setDisplayName(user.companyName).build()
}