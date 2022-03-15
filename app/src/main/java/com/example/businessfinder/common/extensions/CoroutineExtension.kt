package com.example.businessfinder.common.extensions

import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchWhenStarted(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner .repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@launchWhenStarted.collect()
        }
    }
}

fun Query.snapshotAsFlow() = callbackFlow {
    val registration = addSnapshotListener { querySnapshot, exception ->
        exception?.let {
            close(it)
        }
        if (querySnapshot == null) {
            close()
        }
        querySnapshot?.let {
            trySendBlocking(it)
        }
    }

    awaitClose {
        registration.remove()
    }
}

fun DocumentReference.snapshotAsFlow() = callbackFlow {
    val registration = addSnapshotListener { documentSnapshot, exception ->
        exception?.let {
            close(it)
        }
        if (documentSnapshot == null) {
            close()
        }
        documentSnapshot?.let {
            trySendBlocking(it)
        }
    }

    awaitClose {
        registration.remove()
    }
}


