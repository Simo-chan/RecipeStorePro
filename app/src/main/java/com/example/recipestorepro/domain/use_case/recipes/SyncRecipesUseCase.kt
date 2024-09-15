package com.example.recipestorepro.domain.use_case.recipes

import com.example.recipestorepro.domain.repository.RecipeRepo

class SyncRecipesUseCase(private val repo: RecipeRepo) {
    suspend fun syncRecipes() {
        return repo.syncRecipes()
    }
}