package com.example.recipestorepro.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SessionManager(val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("session_manager")

    suspend fun updateSession(token: String, name: String, email: String) {
        val preferenceKey = stringPreferencesKey(JWT_TOKEN_KEY)
        val nameKey = stringPreferencesKey(NAME_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
        context.datastore.edit { preferences ->
            preferences[preferenceKey] = token
            preferences[nameKey] = name
            preferences[emailKey] = email
        }
    }

    suspend fun getJWTToken(): String? {
        val preferenceKey = stringPreferencesKey(JWT_TOKEN_KEY)
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

    suspend fun saveAvatarIndex(index: Int) {
        context.datastore.edit { preferences ->
            preferences[AVATAR_KEY] = index
        }
    }

    val avatarIndexFlow: Flow<Int> = context.datastore.data.map {
        it[AVATAR_KEY] ?: 0
    }

    private companion object {
        const val JWT_TOKEN_KEY = "ONE PIECE IS REAL"
        const val NAME_KEY = "NAME KEY"
        const val EMAIL_KEY = "EMAIL KEY"
        val AVATAR_KEY = intPreferencesKey("AVATAR KEY")
    }
}