package com.example.recipestorepro.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> Log.e("Error:", "$throwable") }
}