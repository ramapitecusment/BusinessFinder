package com.example.businessfinder.di

import com.example.businessfinder.services.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val repositoryModule = module {
    single { UserService() }
    single { ChatService() }
    single { OfferService() }
    single { SphereService() }
    single { StorageService() }
    single { CategoryService() }
    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }
}