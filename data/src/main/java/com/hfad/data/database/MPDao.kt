package com.hfad.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.domain.model.DomainPotion

@Dao
interface MPDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPotion(potion: DomainPotion)

    @Query("SELECT * FROM potions")
    suspend fun getPotionsFromDb(): List<DomainPotion>?

    @Query("DELETE FROM potions WHERE potionId = :potionIndex")
    suspend fun deletePotionByIndex(potionIndex: String)
}