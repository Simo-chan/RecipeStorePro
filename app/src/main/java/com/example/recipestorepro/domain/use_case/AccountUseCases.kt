package com.example.recipestorepro.domain.use_case

data class AccountUseCases(
    val createUser: CreateUser,
    val getUser: GetUser,
    val login: Login,
    val logOut: LogOut
)
