package com.example.businessfinder.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.businessfinder.GlideApp
import com.example.businessfinder.R
import com.example.businessfinder.services.FirebaseServices

fun ImageView.glideProfileImage(urlToImage: String?) {
    urlToImage?.let {
        Glide.with(this)
            .load(it)
            .apply(RequestOptions().error(R.drawable.logo_profile))
            .into(this)
    }
}

fun ImageView.glideImage(urlToImage: String?) {
    urlToImage?.let {
        Glide.with(this)
            .load(it)
            .apply(RequestOptions().error(R.drawable.ic_image))
            .into(this)
    }
}

fun ImageView.glideProfileFirebaseImage(urlToImage: String?) {
    if (urlToImage.isNullOrEmpty()) this.glideProfileImage(urlToImage)
    else {
        val storageReference = FirebaseServices.storageReference(urlToImage)
        GlideApp.with(this)
            .load(storageReference)
            .apply(RequestOptions().error(R.drawable.logo_profile))
            .into(this)
    }
}

fun ImageView.glideFirebaseImage(urlToImage: String?) {
    if (urlToImage.isNullOrEmpty()) this.glideImage(urlToImage)
    else {
        val storageReference = FirebaseServices.storageReference(urlToImage)
        GlideApp.with(this)
            .load(storageReference)
            .apply(RequestOptions().error(R.drawable.ic_image))
            .into(this)
    }
}
