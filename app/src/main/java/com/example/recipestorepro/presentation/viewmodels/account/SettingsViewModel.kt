package com.example.recipestorepro.presentation.viewmodels.account

import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.domain.models.User
import com.example.recipestorepro.domain.use_case.account.GetUserUseCase
import com.example.recipestorepro.domain.use_case.account.LogOutUseCase
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logOutUseCase: LogOutUseCase
) : BaseViewModel() {

    private val _currentUserState = MutableSharedFlow<Result<User>>()
    val currentUserState: SharedFlow<Result<User>> = _currentUserState

    private val _logOutState = MutableSharedFlow<Result<String>>()
    val logOutState: SharedFlow<Result<String>> = _logOutState

    fun getCurrentUser() = viewModelScope.launch(exceptionHandler) {
        _currentUserState.emit(Result.Loading())
        _currentUserState.emit(getUserUseCase.getUser())
    }

    fun logout() = viewModelScope.launch(exceptionHandler) {
        _logOutState.emit(Result.Loading())
        _logOutState.emit(logOutUseCase.logout())
    }
}
