package com.dndbestiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.domain.usecase.DeletePotionFromDbUseCase
import com.domain.usecase.GetPotionsFromDbUseCase
import com.domain.usecase.GetPotionsUseCase
import com.domain.usecase.InsertPotionIntoDbUseCase
import com.hfad.data.repository.MPRepositoryImpl

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MPRepositoryImpl) : ViewModelProvider.Factory {
    private val insertPotionIntoDbUseCase by lazy { InsertPotionIntoDbUseCase(repository = repository) }
    private val getPotionsUseCase by lazy { GetPotionsUseCase(repository = repository) }
    private val deletePotionFromDbUseCase by lazy { DeletePotionFromDbUseCase(repository = repository) }
    private val getPotionsFromDbUseCase by lazy { GetPotionsFromDbUseCase(repository = repository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            insertPotionIntoDbUseCase =  insertPotionIntoDbUseCase,
            getPotionsUseCase = getPotionsUseCase,
            deletePotionFromDbUseCase = deletePotionFromDbUseCase,
            getPotionsFromDbUseCase = getPotionsFromDbUseCase) as T
    }
}