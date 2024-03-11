package com.example.recipestorepro.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.CreateUserUseCase
import com.example.recipestorepro.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _registerState = MutableSharedFlow<Result<String>>()
    val registerState: SharedFlow<Result<String>> = _registerState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(CURRENT_VIEW_MODEL, "$throwable")
    }

    fun createUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch(exceptionHandler) {
        _registerState.emit(Result.Loading())

        val result = createUserUseCase.create(User(name, email, password), confirmPassword)
        _registerState.emit(result)
    }

    private companion object {
        const val CURRENT_VIEW_MODEL = "SignUpViewModel"
    }
}