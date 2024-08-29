package com.example.recipestorepro.domain.use_case.account

import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.AuthRepo
import com.example.recipestorepro.domain.utils.Result

class GetUserUseCase(private val repository: AuthRepo) {
    suspend fun getUser(): Result<User> {
        return repository.getUser()
    }
}