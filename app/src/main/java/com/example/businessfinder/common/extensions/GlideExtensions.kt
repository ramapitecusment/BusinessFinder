package com.example.businessfinder.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.businessfinder.GlideApp
import com.example.businessfinder.R
import com.example.businessfinder.services.FirebaseServices

fun ImageView.glideImage(urlToImage: String?) {
    urlToImage?.let {
        Glide.with(this)
            .load(it)
            .circleCrop()
            .apply(RequestOptions().error(R.drawable.logo_profile))
            .into(this)
    }
}

fun ImageView.glideFirebaseImage(urlToImage: String?) {
    urlToImage?.let {
        val storageReference = FirebaseServices.storageReference(urlToImage)
        GlideApp.with(this)
            .load(storageReference)
            .circleCrop()
            .apply(RequestOptions().error(R.drawable.logo_profile))
            .into(this)
    }
}
