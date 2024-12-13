package com.dicoding.capstone.skindect.viewmodel

import android.net.Uri
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.dicoding.capstone.skindect.data.SettingsPreferences

class MainViewModel(private val preferences: SettingsPreferences) : ViewModel() {
    private val _imageUri = MutableLiveData<Uri?>()
    fun getTheme() = preferences.getThemeSettings().asLiveData()
    val imageUri: LiveData<Uri?> get() = _imageUri

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }
    class Factory(private val preferences: SettingsPreferences) :
            ViewModelProvider.NewInstanceFactory(){
                override fun <T : ViewModel> create(modelClass: Class<T>): T=
                    MainViewModel(preferences) as T
            }
}
