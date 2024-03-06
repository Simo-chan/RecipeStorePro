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
class SignUpViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    private val _registerState = MutableSharedFlow<Result<String>>()
    val registerState: SharedFlow<Result<String>> = _registerState

    private val currentViewModel = "SignUpViewModel"
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(currentViewModel, "$throwable")
    }

    fun createUser(
        name: String, email: String, password: String, confirmPassword: String
    ) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        _registerState.emit(Result.Loading())

        try {
            val result = accountUseCases.createUser(User(name, email, password), confirmPassword)
            _registerState.emit(result)
        } catch (e: IOException) {
            _registerState.emit(Result.Error(e.toString()))
            return@launch
        } catch (e: HttpException) {
            _registerState.emit(Result.Error(e.toString()))
            return@launch
        }
    }
}