package com.domain.repository

import com.domain.models.DomainPotion
import com.domain.models.DomainPotions

interface MPRepository {
    suspend fun getPotions(): DomainPotions?

    suspend fun insertPotionDb(potion: DomainPotion)

    suspend fun getPotionsFromDb(): List<DomainPotion>?

    suspend fun deletePotionFromDbByIndex(potionIndex: String)
}