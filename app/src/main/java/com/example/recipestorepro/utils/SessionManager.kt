package com.example.recipestorepro.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipestorepro.utils.Constants.EMAIL_KEY
import com.example.recipestorepro.utils.Constants.JWT_TOKEN_KEY
import com.example.recipestorepro.utils.Constants.NAME_KEY
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SessionManager(val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("session_manager")
    private val JWT_TOKEN = stringPreferencesKey(JWT_TOKEN_KEY)

    suspend fun updateSession(token: String, name: String, email: String) {
        val nameKey = stringPreferencesKey(NAME_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        context.datastore.edit { preferences ->
            preferences[JWT_TOKEN] = token
            preferences[nameKey] = name
            preferences[emailKey] = email
        }
    }

    suspend fun getJWTToken(): String? {
        val preferences = context.datastore.data.first()
        return preferences[JWT_TOKEN]
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
            it.clear() }
    }

    fun isUserLoggedIn(): Boolean {
        val token = context.datastore.data.map { preferences ->
            preferences[JWT_TOKEN]
        }
        return runBlocking {
            token.first() != null
        }
    }

}