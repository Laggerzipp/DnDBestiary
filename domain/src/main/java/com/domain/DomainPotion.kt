package com.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
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
    val sideEffects: Any?,
    var isFavorite: Boolean = false
): Serializable
