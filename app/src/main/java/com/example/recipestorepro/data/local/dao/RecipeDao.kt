package com.example.recipestorepro.data.local.dao

import androidx.room.Query
import androidx.room.Upsert
import com.example.recipestorepro.data.local.models.LocalRecipe
import kotlinx.coroutines.flow.Flow

interface RecipeDao {

    @Upsert
    suspend fun insertRecipe(recipe: LocalRecipe)

    @Query("SELECT * FROM LocalRecipe WHERE locallyDeleted=0 ORDER BY date DESC")
    fun getRecipesOrderedByDate(): Flow<List<LocalRecipe>>

    @Query("DELETE FROM LocalRecipe WHERE recipeId=:recipeId")
    suspend fun deleteRecipe(recipeId: LocalRecipe)

    @Query("UPDATE LocalRecipe SET locallyDeleted = 1 WHERE recipeId=:recipeId")
    suspend fun deleteRecipeLocally(recipeId: LocalRecipe)

    @Query("SELECT * FROM LocalRecipe WHERE remotelyConnected = 0")
    suspend fun getAllLocalRecipes(recipeId: LocalRecipe): List<LocalRecipe>

    @Query("SELECT * FROM LocalRecipe WHERE locallyDeleted = 1")
    suspend fun getAllLocallyDeletedRecipes(recipe: LocalRecipe): List<LocalRecipe>


}