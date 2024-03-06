package com.example.recipestorepro.domain.use_case

import com.example.recipestorepro.R
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.use_case.CreateUser.Companion.isEmailValid
import com.example.recipestorepro.domain.use_case.CreateUser.Companion.isPasswordValid
import com.example.recipestorepro.domain.utils.ResourceManager
import com.example.recipestorepro.domain.utils.Result

class Login(
    private val repository: RecipeRepo,
    private val resourceManager: ResourceManager
) {

    suspend operator fun invoke(user: User): Result<String> {

        if (user.email.isEmpty() || user.password.isEmpty()) {
            return Result.Error(resourceManager.getString(R.string.fields_empty))
        }

        if (!isEmailValid(user.email)) {
            return Result.Error(resourceManager.getString(R.string.email_error))
        }

        if (!isPasswordValid(user.password)) {
            return Result.Error(resourceManager.getString(R.string.password_error))
        }

        return repository.login(user)
    }
}