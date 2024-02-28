package com.example.recipestorepro.repository

import com.example.recipestorepro.data.remote.models.User
import com.example.recipestorepro.utils.Result

interface RecipeRepo {

    suspend fun createUser(user: User): Result<String>

    suspend fun login(user: User): Result<String>

    suspend fun getUser(): Result<User>

    suspend fun logout(): Result<String>
}