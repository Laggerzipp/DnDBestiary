package com.domain

import com.domain.model.DomainPotion
import com.domain.model.DomainPotions

interface IMPRepository {
    suspend fun getPotions(): DomainPotions?

    suspend fun insertPotionDb(potion: DomainPotion)

    suspend fun getPotionsFromDb(): List<DomainPotion>?

    suspend fun deletePotionFromDbByIndex(potionIndex: String)
}