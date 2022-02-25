package com.example.businessfinder.services

import com.example.businessfinder.common.Constants.KEY_USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

object FirebaseServices {

    val auth
        get() = FirebaseAuth.getInstance()

    val storage
        get() = FirebaseStorage.getInstance()

    val usersCollection = Firebase.firestore.collection(KEY_USERS_COLLECTION)

    fun currentUserStorageRef(userUid: String) = storage.reference.child(userUid)

    fun currentUserDocument(documentId: String) = usersCollection.document(documentId)

}