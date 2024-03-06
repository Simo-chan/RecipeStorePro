package com.example.recipestorepro.domain.use_case

import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result

class GetUser(private val repository: RecipeRepo) {
    suspend operator fun invoke(): Result<User> {
        return repository.getUser()
    }
}