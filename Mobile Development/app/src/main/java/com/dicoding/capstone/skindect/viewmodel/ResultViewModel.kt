package com.dicoding.capstone.skindect.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstone.skindect.repository.ResultRepository
import kotlinx.coroutines.launch
import com.dicoding.capstone.skindect.data.model.getDiseaseRecommendation

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {

    private val _saveResult = MutableLiveData<Result<Unit>>()
    val saveResult: LiveData<Result<Unit>> get() = _saveResult

    private val _isSaving = MutableLiveData<Boolean>()
    val isSaving: LiveData<Boolean> get() = _isSaving

    fun saveData(imageUri: Uri, diagnosis: String, recommendation: String) {
        _isSaving.value = true
        viewModelScope.launch {
            val result = repository.uploadImageAndSaveData(imageUri, diagnosis, recommendation)
            _saveResult.value = result
            _isSaving.value = false
        }
    }
}
