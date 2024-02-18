package com.hfad.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domain.DomainPotion

@Dao
interface MPDao {
    @Insert
    suspend fun insertPotion(potion: DomainPotion)
    @Query("SELECT * FROM potions")
    suspend fun getPotionsFromDb(): List<DomainPotion>?
    @Query("DELETE FROM potions WHERE potionId = :potionIndex")
    suspend fun deletePotionByIndex(potionIndex: String)
}