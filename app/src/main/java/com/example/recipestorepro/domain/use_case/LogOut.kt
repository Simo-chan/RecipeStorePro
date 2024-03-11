package com.example.recipestorepro.domain.use_case

import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result

class LogOut(private val repository: RecipeRepo) {
    suspend operator fun invoke(): Result<String> {
        return repository.logout()
    }
}