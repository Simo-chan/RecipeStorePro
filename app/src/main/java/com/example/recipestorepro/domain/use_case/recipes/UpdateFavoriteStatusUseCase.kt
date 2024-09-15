package com.example.recipestorepro.domain.use_case.recipes

import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result

class UpdateFavoriteStatusUseCase(private val repo: RecipeRepo) {
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean): Result<String> {
        return repo.updateFavoriteStatus(id, isFavorite)
    }
}