package com.example.businessfinder.common

import com.example.businessfinder.R

object Constants {

    const val KEY_USERS_COLLECTION = "users"
    const val KEY_USERS_PICTURE_REFERENCE = "userPictures"

    const val KEY_MESSAGES = "messages"
    const val KEY_CHAT_CHANNELS = "chatChannels"
    const val KEY_ENGAGED_CHAT_CHANNELS = "engagedChatChannels"

    const val KEY_SPHERES = "spheres"
    const val KEY_CATEGORIES = "categories"

    const val notEmpty = "Это поле не может быть пустым"

    const val FILE_PROVIDER = "com.example.businessfinder.fileprovider"
    const val FILE_PREFIX = "temp"
    const val FILE_SUFFIX = ".jpg"
    const val MIMETYPE_IMAGE = "image/*"
    const val SERVER_ALLOWED_IMAGE_TYPE = "image/png"

    val DRAWER_LIST = setOf(
        R.id.profileFragment,
        R.id.categoriesFragment,
        R.id.requestFragment,
        R.id.offersFragment,
        R.id.chatChannelsFragment,
        R.id.analysisFragment,
    )
}