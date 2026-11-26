package com.example.watchlist.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    val darkTheme: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_THEME] ?: false
    }

    suspend fun setDarkTheme(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME] = value
        }
    }

    companion object {
        private val DARK_THEME = booleanPreferencesKey("dark_theme")

        @Volatile
        private var _instance: SettingsRepository? = null

        val instance: SettingsRepository
            get() = _instance ?: error("SettingsRepository is not initialized")

        fun init(context: Context) {
            if (_instance == null) {
                _instance = SettingsRepository(context.applicationContext.settingsDataStore)
            }
        }
    }
}