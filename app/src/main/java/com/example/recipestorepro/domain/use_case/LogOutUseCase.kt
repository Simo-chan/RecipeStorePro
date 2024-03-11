package com.example.recipestorepro.domain.use_case

import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result

class LogOutUseCase(private val repository: RecipeRepo) {
    suspend fun logout(): Result<String> {
        return repository.logout()
    }
}