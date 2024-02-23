package com.dndbestiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.data.repository.MPRepositoryImpl

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MPRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}