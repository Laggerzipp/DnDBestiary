package com.domain.usecase

import com.domain.models.DomainPotions
import com.domain.repository.MPRepository

class GetPotionsUseCase(private val repository: MPRepository) {

    suspend fun execute(): DomainPotions? {
        return repository.getPotions()
    }
}