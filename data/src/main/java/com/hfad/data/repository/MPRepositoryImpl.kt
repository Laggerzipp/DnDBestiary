package com.hfad.data.repository

import com.domain.repository.MPRepository
import com.domain.models.DomainPotion
import com.domain.models.DomainPotions
import com.hfad.data.storage.NetworkStorage
import com.hfad.data.storage.PotionStorage

class MPRepositoryImpl(
    private val potionStorage: PotionStorage, private val networkStorage: NetworkStorage,
) : MPRepository {
    override suspend fun getPotions(): DomainPotions? {
        return networkStorage.getPotions()
    }

    override suspend fun insertPotionIntoDb(potion: DomainPotion) {
        return potionStorage.insertPotionIntoDb(potion = potion)
    }

    override suspend fun getPotionsFromDb(): List<DomainPotion>? {
        return potionStorage.getPotionsFromDb()
    }

    override suspend fun deletePotionFromDbByIndex(potionIndex: String) {
        potionStorage.deletePotionFromDbByIndex(potionIndex = potionIndex)
    }
}