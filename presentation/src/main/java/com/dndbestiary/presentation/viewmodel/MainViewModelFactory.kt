package com.dndbestiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.domain.usecase.DeletePotionFromDbUseCase
import com.domain.usecase.GetPotionsFromDbUseCase
import com.domain.usecase.GetPotionsUseCase
import com.domain.usecase.InsertPotionIntoDbUseCase

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val insertPotionIntoDbUseCase: InsertPotionIntoDbUseCase,
    private val getPotionsUseCase: GetPotionsUseCase,
    private val deletePotionFromDbUseCase: DeletePotionFromDbUseCase,
    private val getPotionsFromDbUseCase: GetPotionsFromDbUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            insertPotionIntoDbUseCase = insertPotionIntoDbUseCase,
            getPotionsUseCase = getPotionsUseCase,
            deletePotionFromDbUseCase = deletePotionFromDbUseCase,
            getPotionsFromDbUseCase = getPotionsFromDbUseCase
        ) as T
    }
}
