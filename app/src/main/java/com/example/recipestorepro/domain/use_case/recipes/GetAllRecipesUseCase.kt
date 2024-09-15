package com.example.recipestorepro.domain.use_case.recipes

import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.Flow

class GetAllRecipesUseCase(private val repo: RecipeRepo) {
    fun getAllRecipes(): Flow<List<LocalRecipe>> {
        return repo.getAllRecipes()
    }
}