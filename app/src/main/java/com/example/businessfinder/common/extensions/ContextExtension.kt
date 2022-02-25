package com.example.businessfinder.common.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.businessfinder.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showAlertDialog(
    title: String?,
    message: String?,
    positiveActionTitleId: Int = R.string.scr_any_lbl_ok,
    negativeActionTitleId: Int? = null,
    positiveAction: (() -> Unit)? = null,
    negativeAction: (() -> Unit)? = null,
    iconId: Int? = null
) {
    val builder = MaterialAlertDialogBuilder(this)

    title?.let { builder.setTitle(title) }
    message?.let { builder.setMessage(message) }
    iconId?.let { builder.setIcon(iconId) }

    builder.setPositiveButton(positiveActionTitleId) { dialog, _ ->
        dialog.dismiss()
        positiveAction?.invoke()
    }

    if (negativeActionTitleId != null) {
        builder.setNegativeButton(negativeActionTitleId) { dialog, _ ->
            dialog.dismiss()
            negativeAction?.invoke()
        }
    }

    builder.create().show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.hasCameraPermission() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.CAMERA
) == PackageManager.PERMISSION_GRANTED

fun Context.hasReadStoragePermission() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.READ_EXTERNAL_STORAGE
) == PackageManager.PERMISSION_GRANTED
