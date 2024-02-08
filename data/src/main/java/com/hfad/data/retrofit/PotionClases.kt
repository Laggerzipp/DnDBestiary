package com.hfad.data.retrofit

import com.domain.DomainPotion
import com.google.gson.annotations.SerializedName

data class PotionResponse(
    @SerializedName("data")
    val potions: List<Potion>,
    val meta: Meta,
    val links: Links
)

data class Potion(
    val id: String,
    val type: String,
    val attributes: PotionAttributes,
    val links: PotionLinks
)

data class PotionAttributes(
    val slug: String,
    val characteristics: String,
    val difficulty: String?,
    val effect: String,
    var image: String,
    val inventors: Any?,
    val ingredients: String,
    val manufacturers: Any?,
    val name: String,
    @SerializedName("side_effects")
    val sideEffects: Any?,
    val time: Any?,
    val wiki: String
)

data class PotionLinks(
    val self: String
)

data class Meta(
    val pagination: Pagination,
    val copyright: String,
    @SerializedName("generated_at")
    val generatedAt: String
)

data class Pagination(
    val current: Int,
    val next: Int,
    val last: Int,
    val records: Int
)

data class Links(
    val self: String,
    val current: String,
    val next: String,
    val last: String
)

fun Potion.toDomain() : DomainPotion{
    return DomainPotion(id, attributes.characteristics,
        attributes.difficulty, attributes.effect,
        attributes.image, attributes.ingredients,
        attributes.name, attributes.sideEffects)
}