package com.example.recipestorepro.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipestorepro.utils.Constants.JWT_TOKEN_KEY
import kotlinx.coroutines.flow.first

class SessionManager(private val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun saveJWTToken(token: String) {
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        context.datastore.edit { preferences ->
            preferences[jwtTokenKey] = token
        }
    }

    suspend fun getJWTToken(): String? {
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        val preferences = context.datastore.data.first()
        return preferences[jwtTokenKey]
    }
}