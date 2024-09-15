package com.example.recipestorepro.domain.use_case.recipes

import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result

class CreateRecipeUseCase(private val repo: RecipeRepo) {
    suspend fun createRecipe(recipe: LocalRecipe): Result<String> {
        return repo.createRecipe(recipe)
    }
}