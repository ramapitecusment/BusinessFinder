package com.example.businessfinder.models

data class AlertDialogModel(
    val title: String? = null,
    val message: String? = null,
    val positiveActionTitleId: String = "Ok",
    val negativeActionTitleId: Int? = null,
    val positiveAction: (() -> Unit)? = null,
    val negativeAction: (() -> Unit)? = null,
    val iconId: Int? = null
)