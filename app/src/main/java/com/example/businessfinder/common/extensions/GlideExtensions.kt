package com.example.businessfinder.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.businessfinder.R

fun ImageView.glideImage(urlToImage: String?) {
    urlToImage?.let {
        Glide.with(this)
            .load(it)
            .circleCrop()
            .apply(RequestOptions().error(R.drawable.logo_profile))
            .into(this)
    }
}
