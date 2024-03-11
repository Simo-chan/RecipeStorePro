package com.example.recipestorepro.domain.use_case

import android.content.res.Resources
import com.example.recipestorepro.R
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.Result
import java.util.regex.Pattern

class CreateUserUseCase(
    private val repository: RecipeRepo,
    private val resources: Resources
) {
    suspend fun create(user: User, confirmPassword: String): Result<String> {

        if (user.name.orEmpty().isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            return Result.Error(resources.getString(R.string.fields_empty))
        }

        if (!isEmailValid(user.email)) {
            return Result.Error(resources.getString(R.string.email_error))
        }

        if (!isPasswordValid(user.password)) {
            return Result.Error(resources.getString(R.string.password_error))
        }

        if (user.password != confirmPassword) {
            return Result.Error(resources.getString(R.string.passwords_mismatching))
        }

        return repository.createUser(user)
    }


    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
        private const val MAX_PASSWORD_LENGTH = 20

        fun isEmailValid(email: String): Boolean {
            val regex =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
            val pattern = Pattern.compile(regex)
            return (email.isNotEmpty() && pattern.matcher(email).matches())
        }

        fun isPasswordValid(password: String): Boolean {
            return (password.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH)
        }
    }
}