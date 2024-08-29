package com.example.recipestorepro.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SessionManager(val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("session_manager")
    private val preferenceKey = stringPreferencesKey(JWT_TOKEN_KEY)

    suspend fun updateSession(token: String, name: String, email: String) {
        val nameKey = stringPreferencesKey(NAME_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        context.datastore.edit { preferences ->
            preferences[preferenceKey] = token
            preferences[nameKey] = name
            preferences[emailKey] = email
        }
    }

    suspend fun getJWTToken(): String? {
        val preferences = context.datastore.data.first()
        return preferences[preferenceKey]
    }

    suspend fun getCurrentUserName(): String? {
        val nameKey = stringPreferencesKey(NAME_KEY)
        val preferences = context.datastore.data.first()
        return preferences[nameKey]
    }

    suspend fun getCurrentUserEmail(): String? {
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        val preferences = context.datastore.data.first()
        return preferences[emailKey]
    }

    suspend fun logout() {
        context.datastore.edit {
            delay(1000)
            it.clear()
        }
    }

    fun isUserLoggedIn(): Boolean {
        return runBlocking {
            getJWTToken() != null
        }
    }

    private companion object {
        const val JWT_TOKEN_KEY = "ONE PIECE IS REAL"
        const val NAME_KEY = "NAME KEY"
        const val EMAIL_KEY = "EMAIL KEY"
    }
}