package com.hfad.data.database

import androidx.room.Insert
import com.domain.DomainPotion

interface MPDao {
    @Insert
    suspend fun insertPotion(potion: DomainPotion)
}