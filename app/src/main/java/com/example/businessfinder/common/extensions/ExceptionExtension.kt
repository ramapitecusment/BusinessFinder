package com.example.businessfinder.common.extensions

import android.app.Application
import com.example.businessfinder.R
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import kotlin.io.AccessDeniedException

fun Throwable.userFriendlyMessage(application: Application): String =
    when (this) {
        is SocketTimeoutException -> application.resources.getString(R.string.scr_any_msg_error_no_connection)
        is TimeoutException -> application.resources.getString(R.string.scr_any_msg_error_timeout)
        is AccessDeniedException -> application.resources.getString(R.string.scr_any_msg_error_access_denied_message)
        else -> application.resources.getString(R.string.scr_any_msg_unknown_error)
    }