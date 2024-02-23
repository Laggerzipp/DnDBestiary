package com.domain.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "potions")
data class DomainPotion(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val potionId: String,
    val characteristics: String?,
    val difficulty: String?,
    val effect: String?,
    var image: String?,
    val ingredients: String?,
    val name: String,
    val sideEffects: String?,
    var isFavorite: Boolean = false,
    var bitmapImage: Bitmap? = null
): Serializable
