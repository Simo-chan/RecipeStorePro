package com.example.recipestorepro.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.GetUserUseCase
import com.example.recipestorepro.domain.use_case.LogOutUseCase
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
    private val getUserUseCase: GetUserUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _currentUserState = MutableSharedFlow<Result<User>>()
    val currentUserState: SharedFlow<Result<User>> = _currentUserState

    private val _logOutState = MutableSharedFlow<Result<String>>()
    val logOutState: SharedFlow<Result<String>> = _logOutState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(CURRENT_VIEW_MODEL, "$throwable")
    }

    fun getCurrentUser() = viewModelScope.launch(
        Dispatchers.IO + exceptionHandler
    ) {
        _currentUserState.emit(Result.Loading())
        _currentUserState.emit(getUserUseCase.getUser())
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        _logOutState.emit(Result.Loading())
        _logOutState.emit(logOutUseCase.logout())
    }

    private companion object {
        const val CURRENT_VIEW_MODEL = "SettingsViewModel"
    }
}

