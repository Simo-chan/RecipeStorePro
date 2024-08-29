package com.example.recipestorepro.domain.repository

import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.utils.Result

interface AuthRepo {

    suspend fun createUser(user: User): Result<String>

    suspend fun login(user: User): Result<String>

    suspend fun getUser(): Result<User>

    suspend fun logout(): Result<String>
}