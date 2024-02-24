package com.domain.usecase

import com.domain.repository.MPRepository

class DeletePotionFromDbUseCase(private val repository: MPRepository) {

    suspend fun execute(potionId: String) {
        repository.deletePotionFromDbByIndex(potionId)
    }
}