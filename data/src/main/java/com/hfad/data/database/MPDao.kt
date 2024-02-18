package com.hfad.data.database

import androidx.room.Insert
import androidx.room.Query
import com.domain.DomainPotion

interface MPDao {
    @Insert
    suspend fun insertPotion(potion: DomainPotion)
    @Query("SELECT * FROM potions")
    suspend fun getPotionsFromDb(): List<DomainPotion>?

}