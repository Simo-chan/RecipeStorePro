package com.example.recipestorepro.domain.repository

import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface RecipeRepo {

    suspend fun createRecipe(recipe: LocalRecipe): Result<String>
    suspend fun updateRecipe(recipe: LocalRecipe): Result<String>
    fun getRecipesBySearchQuery(searchQuery: String): Flow<List<LocalRecipe>>
    fun getAllRecipes(): Flow<List<LocalRecipe>>
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean): Result<String>
    fun getFavoriteRecipes(): Flow<List<LocalRecipe>>
    suspend fun getAllRecipesRemotely()
    suspend fun deleteRecipe(recipeId: String)
    suspend fun syncRecipes()
}