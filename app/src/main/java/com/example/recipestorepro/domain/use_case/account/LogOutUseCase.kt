package com.example.recipestorepro.domain.use_case.account

import com.example.recipestorepro.domain.repository.AuthRepo
import com.example.recipestorepro.domain.utils.Result

class LogOutUseCase(private val repository: AuthRepo) {
    suspend fun logout(): Result<String> {
        return repository.logout()
    }
}