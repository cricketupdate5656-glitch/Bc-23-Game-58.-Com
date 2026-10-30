package com.example.bc23.core.security

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bc23.core.common.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.bc23DataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFS_NAME)

class SessionManager(private val context: Context) {

    private object PreferenceKeys {
        val AUTH_TOKEN = stringPreferencesKey(Constants.KEY_AUTH_TOKEN)
        val USER_ID = stringPreferencesKey(Constants.KEY_USER_ID)
        val USERNAME = stringPreferencesKey(Constants.KEY_USERNAME)
        val USER_UID = stringPreferencesKey(Constants.KEY_USER_UID)
        val IS_LOGGED_IN = booleanPreferencesKey(Constants.KEY_IS_LOGGED_IN)
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey(Constants.KEY_NOTIFICATIONS_ENABLED)
        val SOUND_EFFECTS = booleanPreferencesKey(Constants.KEY_SOUND_EFFECTS)
    }

    val isLoggedInFlow: Flow<Boolean> = context.bc23DataStore.data.map { preferences ->
        preferences[PreferenceKeys.IS_LOGGED_IN] ?: false
    }

    val usernameFlow: Flow<String?> = context.bc23DataStore.data.map { preferences ->
        preferences[PreferenceKeys.USERNAME]
    }

    val uidFlow: Flow<String?> = context.bc23DataStore.data.map { preferences ->
        preferences[PreferenceKeys.USER_UID]
    }

    val authTokenFlow: Flow<String?> = context.bc23DataStore.data.map { preferences ->
        preferences[PreferenceKeys.AUTH_TOKEN]
    }

    val notificationsEnabledFlow: Flow<Boolean> = context.bc23DataStore.data.map { preferences ->
        preferences[PreferenceKeys.NOTIFICATIONS_ENABLED] ?: true
    }

    suspend fun saveSession(
        userId: String,
        username: String,
        uid: String,
        token: String
    ) {
        context.bc23DataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_ID] = userId
            preferences[PreferenceKeys.USERNAME] = username
            preferences[PreferenceKeys.USER_UID] = uid
            preferences[PreferenceKeys.AUTH_TOKEN] = token
            preferences[PreferenceKeys.IS_LOGGED_IN] = true
        }
    }

    suspend fun clearSession() {
        context.bc23DataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_ID)
            preferences.remove(PreferenceKeys.USERNAME)
            preferences.remove(PreferenceKeys.USER_UID)
            preferences.remove(PreferenceKeys.AUTH_TOKEN)
            preferences[PreferenceKeys.IS_LOGGED_IN] = false
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.bc23DataStore.edit { preferences ->
            preferences[PreferenceKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }
}
