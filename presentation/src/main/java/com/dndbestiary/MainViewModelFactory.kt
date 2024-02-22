package com.dndbestiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.data.repository.MPRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MPRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}