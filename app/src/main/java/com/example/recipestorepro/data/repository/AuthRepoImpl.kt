package com.example.recipestorepro.data.repository

import android.content.res.Resources
import com.example.recipestorepro.R
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.data.utils.SessionManager
import com.example.recipestorepro.data.utils.isNetworkConnected
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.AuthRepo
import com.example.recipestorepro.domain.utils.Result
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val sessionManager: SessionManager,
    private val resources: Resources
) : AuthRepo {

    override suspend fun createUser(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error<String>(resources.getString(R.string.no_internet))
            }

            val result = recipeApi.registerAccount(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.name.orEmpty(), user.email)
                Result.Success(resources.getString(R.string.account_created))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }

    override suspend fun login(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error<String>(resources.getString(R.string.no_internet))
            }

            val result = recipeApi.login(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.name.orEmpty(), user.email)
                Result.Success(resources.getString(R.string.logged_in))
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }

    override suspend fun getUser(): Result<User> {
        return try {
            val name = sessionManager.getCurrentUserName()
            val email = sessionManager.getCurrentUserEmail()
            if (name == null || email == null) {
                Result.Error<String>(resources.getString(R.string.not_logged_in))
            }
            Result.Success(null, User(name, email.orEmpty(), ""))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }

    override suspend fun logout(): Result<String> {
        return try {
            sessionManager.logout()
            Result.Success(resources.getString(R.string.logged_out))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: resources.getString(R.string.problem))
        }
    }
}