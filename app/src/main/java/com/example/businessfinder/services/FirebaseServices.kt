package com.example.businessfinder.services

import com.example.businessfinder.common.Constants.KEY_CATEGORIES
import com.example.businessfinder.common.Constants.KEY_CHAT_CHANNELS
import com.example.businessfinder.common.Constants.KEY_MESSAGES
import com.example.businessfinder.common.Constants.KEY_OFFERS
import com.example.businessfinder.common.Constants.KEY_SPHERES
import com.example.businessfinder.common.Constants.KEY_USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

object FirebaseServices {

    val auth
        get() = FirebaseAuth.getInstance()

    private val storage
        get() = FirebaseStorage.getInstance()

    val usersCollection
        get() = Firebase.firestore.collection(KEY_USERS_COLLECTION)

    val chatChannelsCollection
        get() = Firebase.firestore.collection(KEY_CHAT_CHANNELS)

    val categoriesCollection
        get() = Firebase.firestore.collection(KEY_CATEGORIES)

    val spheresCollection
        get() = Firebase.firestore.collection(KEY_SPHERES)

    val offersCollection
        get() = Firebase.firestore.collection(KEY_OFFERS)

    fun storageReference(path: String) = FirebaseStorage.getInstance().getReference(path)

    fun currentUserStorageRef(userUid: String) = storage.reference.child(userUid)

    fun currentUserDocument(documentId: String) = usersCollection.document(documentId)

    fun currentChatChannelMessages(channelId: String) = chatChannelsCollection.document(channelId)
        .collection(KEY_MESSAGES).orderBy("time")

}