package com.example.recipestorepro.domain.use_case

import android.content.res.Resources
import com.example.recipestorepro.R
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.use_case.CreateUserUseCase.Companion.isEmailValid
import com.example.recipestorepro.domain.use_case.CreateUserUseCase.Companion.isPasswordValid
import com.example.recipestorepro.domain.utils.Result

class LoginUseCase(
    private val repository: RecipeRepo,
    private val resources: Resources
) {

    suspend fun login(user: User): Result<String> {

        if (user.email.isEmpty() || user.password.isEmpty()) {
            return Result.Error(resources.getString(R.string.fields_empty))
        }

        if (!isEmailValid(user.email)) {
            return Result.Error(resources.getString(R.string.email_error))
        }

        if (!isPasswordValid(user.password)) {
            return Result.Error(resources.getString(R.string.password_error))
        }

        return repository.login(user)
    }
}