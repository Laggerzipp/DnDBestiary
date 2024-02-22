package com.domain.useCases

import com.domain.IMPRepository
import com.domain.model.DomainPotion
import com.domain.model.DomainPotions

class UseCases(private val repository: IMPRepository) {

    suspend fun insertPotionIntoDb(potion: DomainPotion){
        repository.insertPotionDb(potion)
    }

    suspend fun deletePotionFromDbByIndex(potionId: String){
        repository.deletePotionFromDbByIndex(potionId)
    }

    suspend fun getPotions(): DomainPotions?{
       return repository.getPotions()
    }

    suspend fun getPotionsFromDb(): List<DomainPotion>?{
        return repository.getPotionsFromDb()
    }
}