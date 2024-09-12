package com.example.recipestorepro.presentation.viewmodels.account

import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.account.LoginUseCase
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginState = MutableSharedFlow<Result<String>>()
    val loginState: SharedFlow<Result<String>> = _loginState

    fun login(name: String, email: String, password: String) =
        viewModelScope.launch(exceptionHandler) {
            _loginState.emit(Result.Loading())

            val result = loginUseCase.login(User(name, email, password))
            _loginState.emit(result)
        }
}