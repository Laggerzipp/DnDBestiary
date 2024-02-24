package com.domain.usecase

import com.domain.repository.MPRepository
import com.domain.models.DomainPotion

class InsertPotionIntoDbUseCase(private val repository: MPRepository) {

    suspend fun execute(potion: DomainPotion) {
        repository.insertPotionIntoDb(potion)
    }
}