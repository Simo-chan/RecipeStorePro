package com.example.recipestorepro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.data.remote.models.User
import com.example.recipestorepro.repository.RecipeRepo
import com.example.recipestorepro.utils.Constants.EMAIL_ERROR
import com.example.recipestorepro.utils.Constants.FIELDS_EMPTY
import com.example.recipestorepro.utils.Constants.MAX_PASSWORD_LENGTH
import com.example.recipestorepro.utils.Constants.MIN_PASSWORD_LENGTH
import com.example.recipestorepro.utils.Constants.PASSWORDS_MISMATCHING
import com.example.recipestorepro.utils.Constants.PASSWORD_ERROR
import com.example.recipestorepro.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val recipeRepo: RecipeRepo
) : ViewModel() {

    private val _registerState = MutableSharedFlow<Result<String>>()
    val registerState: SharedFlow<Result<String>> = _registerState

    private val _loginState = MutableSharedFlow<Result<String>>()
    val loginState: SharedFlow<Result<String>> = _loginState

    private val _currentUserState = MutableSharedFlow<Result<User>>()
    val currentUserState: SharedFlow<Result<User>> = _currentUserState


    fun createUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        _registerState.emit(Result.Loading())

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _registerState.emit(Result.Error(FIELDS_EMPTY))
            return@launch
        }

        if (!isEmailValid(email)) {
            _registerState.emit(Result.Error(EMAIL_ERROR))
            return@launch
        }

        if (!isPasswordValid(password)) {
            _registerState.emit(Result.Error(PASSWORD_ERROR))
        }

        if (password != confirmPassword) {
            _registerState.emit(Result.Error(PASSWORDS_MISMATCHING))
        }

        val newUser = User(
            name,
            email,
            password
        )
        _registerState.emit(recipeRepo.createUser(newUser))
    }

    fun loginUser(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginState.emit(Result.Loading())

        if (email.isEmpty() || password.isEmpty()) {
            _loginState.emit(Result.Error(FIELDS_EMPTY))
            return@launch
        }

        if (!isEmailValid(email)) {
            _loginState.emit(Result.Error(EMAIL_ERROR))
            return@launch
        }

        if (!isPasswordValid(password)) {
            _loginState.emit(Result.Error(PASSWORD_ERROR))
        }


        val newUser = User(
            null,
            email,
            password
        )
        _loginState.emit(recipeRepo.login(newUser))
    }

    fun getCurrentUser() = viewModelScope.launch {
        _currentUserState.emit(Result.Loading())
        _currentUserState.emit(recipeRepo.getUser())
    }

    fun logout() = viewModelScope.launch {
        val result = recipeRepo.logout()
        if (result is Result.Success) {
            getCurrentUser()
        }
    }


    private fun isEmailValid(email: String): Boolean {
        val regex =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }

    private fun isPasswordValid(password: String): Boolean {
        return (password.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH)
    }

}