package com.example.recipestorepro.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.AccountUseCases
import com.example.recipestorepro.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    private val _loginState = MutableSharedFlow<Result<String>>()
    val loginState: SharedFlow<Result<String>> = _loginState

    private val currentViewModel = "LoginViewModel"
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(currentViewModel, "$throwable")
    }

    fun login(name: String, password: String) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _loginState.emit(Result.Loading())
            try {
                val result = accountUseCases.login(User("", name, password))
                _loginState.emit(result)
            } catch (e: IOException) {
                _loginState.emit(Result.Error(e.toString()))
                return@launch
            } catch (e: HttpException) {
                _loginState.emit(Result.Error(e.toString()))
                return@launch
            }
        }
}