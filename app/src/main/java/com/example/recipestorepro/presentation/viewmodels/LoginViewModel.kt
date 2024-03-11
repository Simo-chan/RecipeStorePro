package com.example.recipestorepro.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.LoginUseCase
import com.example.recipestorepro.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableSharedFlow<Result<String>>()
    val loginState: SharedFlow<Result<String>> = _loginState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(CURRENT_VIEW_MODEL, "$throwable")
    }

    fun login(name: String, password: String) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _loginState.emit(Result.Loading())

            val result = loginUseCase.login(User("", name, password))
            _loginState.emit(result)
        }

    private companion object {
        const val CURRENT_VIEW_MODEL = "LoginViewModel"
    }
}