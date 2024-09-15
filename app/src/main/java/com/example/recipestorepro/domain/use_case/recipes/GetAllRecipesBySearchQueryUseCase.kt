package com.example.recipestorepro.domain.use_case.recipes

import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.Flow

class GetAllRecipesBySearchQueryUseCase(private val repo: RecipeRepo) {
    fun getRecipesBySearchQuery(searchQuery: String): Flow<List<LocalRecipe>> {
        return repo.getRecipesBySearchQuery("%$searchQuery%")
    }
}