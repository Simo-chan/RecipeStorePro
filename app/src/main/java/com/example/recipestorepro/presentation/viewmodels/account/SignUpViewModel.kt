package com.example.recipestorepro.presentation.viewmodels.account

import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.account.CreateUserUseCase
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : BaseViewModel() {

    private val _registerState = MutableSharedFlow<Result<String>>()
    val registerState: SharedFlow<Result<String>> = _registerState

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
}