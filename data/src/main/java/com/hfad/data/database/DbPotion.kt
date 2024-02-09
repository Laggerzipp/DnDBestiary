package com.hfad.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.domain.DomainPotion

@Entity
class DbPotion(){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "potionId")
    var potionId: String? = null

    @ColumnInfo(name = "characteristics")
    var characteristics: String? = null

    @ColumnInfo(name = "difficulty")
    var difficulty: String? = null

    @ColumnInfo(name = "effect")
    var effect: String? = null

    @ColumnInfo(name = "image")
    var image: String? = null

    @ColumnInfo(name = "ingredients")
    var ingredients: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "sideEffects")
    var sideEffects: String? = null

    constructor(potion: DomainPotion) : this() {
        potionId = potion.id
        characteristics = potion.characteristics
        difficulty = potion.difficulty
        effect = potion.effect
        image = potion.image
        ingredients = potion.ingredients
        name = potion.name
        sideEffects = potion.sideEffects?.toString()
    }
}
