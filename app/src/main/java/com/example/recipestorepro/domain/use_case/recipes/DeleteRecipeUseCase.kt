package com.example.recipestorepro.domain.use_case.recipes

import com.example.recipestorepro.domain.repository.RecipeRepo

class DeleteRecipeUseCase(private val repo: RecipeRepo) {
    suspend fun deleteRecipe(recipeId: String) {
        return repo.deleteRecipe(recipeId)
    }
}