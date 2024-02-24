package com.dndbestiary.di

import com.domain.repository.MPRepository
import com.domain.usecase.DeletePotionFromDbUseCase
import com.domain.usecase.GetPotionsFromDbUseCase
import com.domain.usecase.GetPotionsUseCase
import com.domain.usecase.InsertPotionIntoDbUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideInsertPotionIntoDbUseCase(repository: MPRepository): InsertPotionIntoDbUseCase {
        return InsertPotionIntoDbUseCase(repository = repository)
    }

    @Provides
    fun provideGetPotionsUseCase(repository: MPRepository): GetPotionsUseCase {
        return GetPotionsUseCase(repository = repository)
    }

    @Provides
    fun provideDeletePotionFromDbUseCase(repository: MPRepository): DeletePotionFromDbUseCase {
        return DeletePotionFromDbUseCase(repository = repository)
    }

    @Provides
    fun provideGetPotionsFromDbUseCase(repository: MPRepository): GetPotionsFromDbUseCase {
        return GetPotionsFromDbUseCase(repository = repository)
    }
}