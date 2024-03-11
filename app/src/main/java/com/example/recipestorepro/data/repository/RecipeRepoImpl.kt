package com.example.recipestorepro.data.repository

import com.example.recipestorepro.R
import com.example.recipestorepro.data.local.RecipeDao
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.ResourceManager
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.data.utils.SessionManager
import com.example.recipestorepro.data.utils.isNetworkConnected
import javax.inject.Inject

class RecipeRepoImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDao: RecipeDao,
    private val sessionManager: SessionManager,
    private val resourceManager: ResourceManager
) : RecipeRepo {

    override suspend fun createUser(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error<String>(resourceManager.getString(R.string.no_internet))
            }

            val result = recipeApi.registerAccount(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.name.orEmpty(), user.email)
                Result.Success(resourceManager.getString(R.string.account_created))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resourceManager.getString(R.string.problem))
        }
    }


    override suspend fun login(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error<String>(resourceManager.getString(R.string.no_internet))
            }

            val result = recipeApi.login(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.name.orEmpty(), user.email)
                Result.Success(resourceManager.getString(R.string.logged_out))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resourceManager.getString(R.string.problem))
        }
    }

    override suspend fun getUser(): Result<User> {
        return try {
            val name = sessionManager.getCurrentUserName()
            val email = sessionManager.getCurrentUserEmail()
            if (name == null || email == null) {
                Result.Error<String>(resourceManager.getString(R.string.not_logged_in))
            }
            Result.Success(null, User(name, email.orEmpty(), ""))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resourceManager.getString(R.string.problem))
        }
    }

    override suspend fun logout(): Result<String> {
        return try {
            sessionManager.logout()
            Result.Success(resourceManager.getString(R.string.logged_out))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resourceManager.getString(R.string.problem))
        }
    }
}