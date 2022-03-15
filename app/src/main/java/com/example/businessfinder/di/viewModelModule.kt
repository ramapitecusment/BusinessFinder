package com.example.businessfinder.di

import com.example.businessfinder.scenes.category.CategoriesViewModel
import com.example.businessfinder.scenes.chat.ChatViewModel
import com.example.businessfinder.scenes.chatchannels.ChatChannelsViewModel
import com.example.businessfinder.scenes.login.LoginViewModel
import com.example.businessfinder.scenes.offer.OfferViewModel
import com.example.businessfinder.scenes.offers.OffersViewModel
import com.example.businessfinder.scenes.profile.ProfileViewModel
import com.example.businessfinder.scenes.registration.RegistrationViewModel
import com.example.businessfinder.scenes.request.RequestViewModel
import com.example.businessfinder.scenes.sphere.SphereViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { OfferViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { SphereViewModel(get()) }
    viewModel { RequestViewModel(get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { ChatViewModel(get(), get()) }
    viewModel { ChatChannelsViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { OffersViewModel(get(), get(), get()) }
}