package com.domain.usecase

import com.domain.models.DomainPotion
import com.domain.repository.MPRepository

class GetPotionsFromDbUseCase(private val repository: MPRepository) {

    suspend fun execute(): List<DomainPotion>? {
        return repository.getPotionsFromDb()
    }
}