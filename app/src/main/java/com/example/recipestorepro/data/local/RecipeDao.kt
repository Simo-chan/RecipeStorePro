package com.example.recipestorepro.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.recipestorepro.domain.models.LocalRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Upsert
    suspend fun insertRecipe(recipe: LocalRecipe)

    @Query("SELECT * FROM LocalRecipe WHERE locallyDeleted=0 ORDER BY date DESC")
    fun getRecipesOrderedByDate(): Flow<List<LocalRecipe>>

    @Query("DELETE FROM LocalRecipe WHERE recipeId=:recipeId")
    suspend fun deleteRecipe(recipeId: String)

    @Query("UPDATE LocalRecipe SET locallyDeleted = 1 WHERE recipeId=:recipeId")
    suspend fun deleteRecipeLocally(recipeId: String)

    @Query("SELECT * FROM LocalRecipe WHERE remotelyConnected = 0")
    suspend fun getAllLocalRecipes(): List<LocalRecipe>

    @Query("SELECT * FROM LocalRecipe WHERE locallyDeleted = 1")
    suspend fun getAllLocallyDeletedRecipes(): List<LocalRecipe>


}