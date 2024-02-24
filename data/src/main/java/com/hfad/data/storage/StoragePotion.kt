package com.hfad.data.storage

import android.content.Context
import com.domain.models.DomainPotion
import com.hfad.data.database.MPDatabase

class StoragePotion(context: Context) : PotionStorage {
    private val db = MPDatabase.getDb(context)
    override suspend fun insertPotionIntoDb(potion: DomainPotion) {
        return db.getDao().insertPotion(potion)
    }

    override suspend fun getPotionsFromDb(): List<DomainPotion>? {
        return db.getDao().getPotionsFromDb()
    }

    override suspend fun deletePotionFromDbByIndex(potionIndex: String) {
        db.getDao().deletePotionByIndex(potionIndex)
    }

}