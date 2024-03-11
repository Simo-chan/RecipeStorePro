package com.example.recipestorepro.domain.use_case

import com.example.recipestorepro.R
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.utils.ResourceManager
import com.example.recipestorepro.domain.utils.Result
import java.util.regex.Pattern

class CreateUser(
    private val repository: RecipeRepo,
    private val resourceManager: ResourceManager
) {
    suspend operator fun invoke(user: User, confirmPassword: String): Result<String> {

        if (user.name!!.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            return Result.Error(resourceManager.getString(R.string.fields_empty))
        }

        if (!isEmailValid(user.email)) {
            return Result.Error(resourceManager.getString(R.string.email_error))
        }

        if (!isPasswordValid(user.password)) {
            return Result.Error(resourceManager.getString(R.string.password_error))
        }

        if (user.password != confirmPassword) {
            return Result.Error(resourceManager.getString(R.string.passwords_mismatching))
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