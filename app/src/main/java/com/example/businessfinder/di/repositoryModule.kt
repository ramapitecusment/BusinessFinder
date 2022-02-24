package com.example.businessfinder.di

import com.example.businessfinder.services.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val repositoryModule = module {
    single { UserService(get(), get()) }
    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }

}