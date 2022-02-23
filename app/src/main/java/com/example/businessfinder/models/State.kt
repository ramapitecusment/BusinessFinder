package com.example.businessfinder.models

open class State(
    var isSuccess: Boolean = false,
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var errorMsg: Throwable? = null
)