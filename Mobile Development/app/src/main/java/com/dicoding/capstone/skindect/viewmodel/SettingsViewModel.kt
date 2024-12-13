package com.dicoding.capstone.skindect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.capstone.skindect.data.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref:SettingsPreferences) : ViewModel() {
    fun getTheme() = pref.getThemeSettings().asLiveData()
    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSettings(isDark)
        }
    }

    class Factory(private val pref: SettingsPreferences): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(pref) as T
    }
}