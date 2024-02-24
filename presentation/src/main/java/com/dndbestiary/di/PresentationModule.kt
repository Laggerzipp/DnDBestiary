package com.dndbestiary.di

import android.content.Context
import com.dndbestiary.presentation.viewmodel.MainViewModelFactory
import com.domain.usecase.DeletePotionFromDbUseCase
import com.domain.usecase.GetPotionsFromDbUseCase
import com.domain.usecase.GetPotionsUseCase
import com.domain.usecase.InsertPotionIntoDbUseCase
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideMainViewModelFactory(
        insertPotionIntoDbUseCase: InsertPotionIntoDbUseCase,
        getPotionsUseCase: GetPotionsUseCase,
        deletePotionFromDbUseCase: DeletePotionFromDbUseCase,
        getPotionsFromDbUseCase: GetPotionsFromDbUseCase,
    ): MainViewModelFactory {
        return MainViewModelFactory(
            insertPotionIntoDbUseCase = insertPotionIntoDbUseCase,
            getPotionsUseCase = getPotionsUseCase,
            deletePotionFromDbUseCase = deletePotionFromDbUseCase,
            getPotionsFromDbUseCase = getPotionsFromDbUseCase
        )
    }
}