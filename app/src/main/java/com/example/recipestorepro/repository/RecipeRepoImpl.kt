package com.example.recipestorepro.repository

import com.example.recipestorepro.data.local.dao.RecipeDao
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.data.remote.models.User
import com.example.recipestorepro.utils.Constants.ACCOUNT_CREATED
import com.example.recipestorepro.utils.Constants.LOGGED_IN
import com.example.recipestorepro.utils.Constants.LOGGED_OUT
import com.example.recipestorepro.utils.Constants.NOT_LOGGED_IN
import com.example.recipestorepro.utils.Constants.NO_INTERNET
import com.example.recipestorepro.utils.Constants.PROBLEM
import com.example.recipestorepro.utils.SessionManager
import com.example.recipestorepro.utils.isNetworkConnected
import com.example.recipestorepro.utils.Result
import java.lang.Exception
import javax.inject.Inject

class RecipeRepoImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDao: RecipeDao,
    private val sessionManager: SessionManager
) : RecipeRepo {

    override suspend fun createUser(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error<String>(NO_INTERNET)
            }

            val result = recipeApi.registerAccount(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.name ?: "", user.email)
                Result.Success(ACCOUNT_CREATED)
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: PROBLEM)
        }
    }


    override suspend fun login(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)) {
                Result.Error<String>(NO_INTERNET)
            }

            val result = recipeApi.login(user)
            if (result.success) {
                sessionManager.updateSession(result.message, user.name ?: "", user.email)
                Result.Success(LOGGED_IN)
            } else {
                Result.Error(result.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: PROBLEM)
        }
    }

    override suspend fun getUser(): Result<User> {
        return try {
            val name = sessionManager.getCurrentUserName()
            val email = sessionManager.getCurrentUserEmail()
            if (name == null || email == null) {
                Result.Error<String>(NOT_LOGGED_IN)
            }
            Result.Success(User(name, email!!, ""))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: PROBLEM)
        }
    }

    override suspend fun logout(): Result<String> {
        return try {
            sessionManager.logout()
            Result.Success(LOGGED_OUT)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: PROBLEM)
        }
    }
}