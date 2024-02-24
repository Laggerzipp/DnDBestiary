package com.hfad.data.storage

import com.domain.models.DomainPotion

interface PotionStorage {

    suspend fun insertPotionIntoDb(potion: DomainPotion)

    suspend fun getPotionsFromDb(): List<DomainPotion>?

    suspend fun deletePotionFromDbByIndex(potionIndex: String)
}