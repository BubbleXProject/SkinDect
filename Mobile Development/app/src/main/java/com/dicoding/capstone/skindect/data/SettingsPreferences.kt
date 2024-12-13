package com.dicoding.capstone.skindect.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.capstone.skindect.settings.SettingsActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")
class SettingsPreferences constructor(context: Context){
    private val settingsDataStore = context.prefDataStore
    private val themeKey = booleanPreferencesKey("theme_settings")

    fun getThemeSettings(): Flow<Boolean> =
        settingsDataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }

    suspend fun saveThemeSettings(isDarkModeActivity: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActivity
        }
    }
}