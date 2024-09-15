package com.example.recipestorepro.domain.utils

sealed class Result<T>(val data: T? = null, val resultMessage: String? = null) {

    class Success<T>(successMessage: String? = null, data: T? = null) :
        Result<T>(data, successMessage)
    class Error<T>(errorMessage: String?, data: T? = null) : Result<T>(data, errorMessage)
    class Loading<T> : Result<T>()
}