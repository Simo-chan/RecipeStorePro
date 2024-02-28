package com.example.recipestorepro.utils

object Constants {

    const val JWT_TOKEN_KEY = "ONE PIECE IS REAL"
    const val NAME_KEY = "NAME KEY"
    const val EMAIL_KEY = "EMAIL KEY"

    const val BASE_URL = "https://recipe-store-pro-api-32d5feef64b4.herokuapp.com"
    const val API_VERSION = "/v1"

    const val NO_INTERNET = "No Internet connection"
    const val ACCOUNT_CREATED = "Account created successfully!"
    const val LOGGED_IN = "Logged in successfully!"
    const val PROBLEM = "Some problem occurred!"
    const val NOT_LOGGED_IN = "User is not logged in!"
    const val LOGGED_OUT = "Logged out successfully!"
    const val FIELDS_EMPTY = "Some fields are empty!"
    const val EMAIL_ERROR = "This email is not valid!"

    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_PASSWORD_LENGTH = 20
    const val PASSWORD_ERROR =
        "Password's length should be between $MIN_PASSWORD_LENGTH and $MAX_PASSWORD_LENGTH "
    const val PASSWORDS_MISMATCHING = "Passwords should be the same!"
}