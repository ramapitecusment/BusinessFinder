package com.example.businessfinder.models

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    class Failure(val msg: Throwable) : Result<Nothing>()
    class Success<T>(val data: T) : Result<T>()
}