package com.example.recipestorepro.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Upsert
    suspend fun upsertRecipe(recipe: LocalRecipe)

    @Query("SELECT * FROM recipes WHERE recipeTitle LIKE :searchQuery OR recipeIngredients LIKE :searchQuery")
    fun getRecipesBySearchQuery(searchQuery: String): Flow<List<LocalRecipe>>

    @Query("SELECT * FROM recipes WHERE locallyDeleted=0 ORDER BY date DESC")
    fun getRecipesOrderedByDate(): Flow<List<LocalRecipe>>

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE recipeId = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM recipes WHERE isFavorite=1")
    fun getFavoriteRecipes(): Flow<List<LocalRecipe>>

    @Query("DELETE FROM recipes WHERE recipeId=:recipeId")
    suspend fun deleteRecipe(recipeId: String)

    @Query("UPDATE recipes SET locallyDeleted = 1 WHERE recipeId=:recipeId")
    suspend fun deleteRecipeLocally(recipeId: String)

    @Query("DELETE FROM recipes")
    suspend fun clearAllRecipes()

    @Query("SELECT * FROM recipes WHERE remotelyConnected = 0")
    suspend fun getAllLocalRecipes(): List<LocalRecipe>

    @Query("SELECT * FROM recipes WHERE locallyDeleted = 1")
    suspend fun getAllLocallyDeletedRecipes(): List<LocalRecipe>
}