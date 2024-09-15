package com.example.recipestorepro.data.repository

import android.content.res.Resources
import android.util.Log
import com.example.recipestorepro.R
import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.data.local.RecipeDao
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.data.remote.RemoteRecipeDTO
import com.example.recipestorepro.data.utils.SessionManager
import com.example.recipestorepro.data.utils.isNetworkConnected
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepoImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDao: RecipeDao,
    private val sessionManager: SessionManager,
    private val resources: Resources
) : RecipeRepo {

    override suspend fun createRecipe(recipe: LocalRecipe): Result<String> {
        try {
            recipeDao.upsertRecipe(recipe)

            if (!isNetworkConnected(sessionManager.context)) {
                return Result.Success(resources.getString(R.string.recipe_saved_locally))
            }

            val token = sessionManager.getJWTToken()
                ?: return Result.Error(resources.getString(R.string.problem))

            val result = recipeApi.createRecipe(
                "Bearer $token", RemoteRecipeDTO(
                    id = recipe.recipeId,
                    title = recipe.recipeTitle,
                    ingredients = recipe.recipeIngredients,
                    instructions = recipe.recipeInstructions,
                    date = recipe.date,
                    isFavorite = recipe.isFavorite
                )
            )

            return if (result.success) {
                recipeDao.upsertRecipe(recipe.also { it.remotelyConnected = true })
                Result.Success(resources.getString(R.string.recipe_saved))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }

    override suspend fun updateRecipe(recipe: LocalRecipe): Result<String> {
        try {
            recipeDao.upsertRecipe(recipe)

            if (!isNetworkConnected(sessionManager.context)) {
                return Result.Success(resources.getString(R.string.recipe_updated_locally))
            }

            val token = sessionManager.getJWTToken()
                ?: return Result.Error(resources.getString(R.string.problem))

            val result = recipeApi.updateRecipe(
                "Bearer $token", RemoteRecipeDTO(
                    id = recipe.recipeId,
                    title = recipe.recipeTitle,
                    ingredients = recipe.recipeIngredients,
                    instructions = recipe.recipeInstructions,
                    date = recipe.date,
                    isFavorite = recipe.isFavorite
                )
            )

            return if (result.success) {
                recipeDao.upsertRecipe(recipe.also { it.remotelyConnected = true })
                Result.Success(resources.getString(R.string.recipe_updated))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }

    override suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean): Result<String> {
        try {
            recipeDao.updateFavoriteStatus(id, isFavorite)

            if (!isNetworkConnected(sessionManager.context)) {
                return Result.Success(resources.getString(R.string.recipe_updated_locally))
            }

            val token = sessionManager.getJWTToken()
                ?: return Result.Error(resources.getString(R.string.problem))

            val result = recipeApi.updateFavoriteStatus(
                "Bearer $token", id, isFavorite
            )
            Log.d("TAG", "updateFavoriteStatus: ${result.message}")

            return if (result.success) {
                Result.Success(resources.getString(R.string.fav_stat_updated))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }

    override fun getRecipesBySearchQuery(searchQuery: String): Flow<List<LocalRecipe>> =
        recipeDao.getRecipesBySearchQuery(searchQuery)

    override fun getAllRecipes(): Flow<List<LocalRecipe>> = recipeDao.getRecipesOrderedByDate()

    override fun getFavoriteRecipes(): Flow<List<LocalRecipe>> = recipeDao.getFavoriteRecipes()

    override suspend fun getAllRecipesRemotely() {
        try {
            val token = sessionManager.getJWTToken() ?: return
            if (!isNetworkConnected(sessionManager.context)) {
                return
            }
            val result = recipeApi.getAllRecipes("Bearer $token")
            result.forEach { remoteRecipe ->
                recipeDao.upsertRecipe(
                    LocalRecipe(
                        recipeTitle = remoteRecipe.title,
                        recipeIngredients = remoteRecipe.ingredients,
                        recipeInstructions = remoteRecipe.instructions,
                        remotelyConnected = true,
                        date = remoteRecipe.date,
                        recipeId = remoteRecipe.id,
                        isFavorite = remoteRecipe.isFavorite
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteRecipe(recipeId: String) {
        try {
            recipeDao.deleteRecipeLocally(recipeId)
            if (!isNetworkConnected(sessionManager.context)) {
                return
            }

            val token = sessionManager.getJWTToken()
            val result = recipeApi.deleteRecipe("Bearer $token", recipeId)
            if (result.success) {
                recipeDao.deleteRecipe(recipeId)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun syncRecipes() {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return
            }

            val locallyDeleted = recipeDao.getAllLocallyDeletedRecipes()
            if (locallyDeleted.isNotEmpty()) {
                locallyDeleted.forEach {
                    deleteRecipe(it.recipeId)
                }
            }

            val notConnected = recipeDao.getAllLocalRecipes()
            if (notConnected.isNotEmpty()) {
                notConnected.forEach {
                    createRecipe(it)
                }
            }

            val notUpdated = recipeDao.getAllLocalRecipes()
            if (notUpdated.isNotEmpty()) {
                notUpdated.forEach {
                    updateRecipe(it)
                }
            }

            getAllRecipesRemotely()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}