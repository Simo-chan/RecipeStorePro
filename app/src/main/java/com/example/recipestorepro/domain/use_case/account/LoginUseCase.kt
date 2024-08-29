package com.example.recipestorepro.domain.use_case.account

import android.content.res.Resources
import com.example.recipestorepro.R
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.AuthRepo
import com.example.recipestorepro.domain.use_case.account.CreateUserUseCase.Companion.isEmailValid
import com.example.recipestorepro.domain.use_case.account.CreateUserUseCase.Companion.isPasswordValid
import com.example.recipestorepro.domain.utils.Result

class LoginUseCase(
    private val repository: AuthRepo,
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