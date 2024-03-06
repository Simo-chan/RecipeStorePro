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
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {

    private val _currentUserState = MutableSharedFlow<Result<User>>()
    val currentUserState: SharedFlow<Result<User>> = _currentUserState

    private val _logOutState = MutableSharedFlow<Result<String>>()
    val logOutState: SharedFlow<Result<String>> = _logOutState

    private val currentViewModel = "SettingsViewModel"
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(currentViewModel, "$throwable")
    }

    fun getCurrentUser() = viewModelScope.launch(
        Dispatchers.IO + exceptionHandler
    ) {
        _currentUserState.emit(Result.Loading())

        try {
            _currentUserState.emit(accountUseCases.getUser())
        } catch (e: Exception) {
            _currentUserState.emit(Result.Error(e.toString()))
            return@launch
        }
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        _logOutState.emit(Result.Loading())

        try {
            _logOutState.emit(accountUseCases.logOut())
        } catch (e: Exception) {
            _logOutState.emit(Result.Error(e.toString()))
            return@launch
        }
    }
}

