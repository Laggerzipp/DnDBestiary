package com.hfad.data.retrofit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PotionResponse(
    @SerializedName("data")
    val potions: List<Potion>,
    val meta: Meta,
    val links: Links
): Serializable

data class Potion(
    val id: String,
    val type: String,
    val attributes: PotionAttributes,
    val links: PotionLinks
): Serializable

data class PotionAttributes(
    val slug: String,
    val characteristics: String,
    val difficulty: String?,
    val effect: String,
    val image: String,
    val inventors: Any?,
    val ingredients: String,
    val manufacturers: Any?,
    val name: String,
    @SerializedName("side_effects")
    val sideEffects: Any?,
    val time: Any?,
    val wiki: String
): Serializable

data class PotionLinks(
    val self: String
): Serializable

data class Meta(
    val pagination: Pagination,
    val copyright: String,
    @SerializedName("generated_at")
    val generatedAt: String
): Serializable

data class Pagination(
    val current: Int,
    val next: Int,
    val last: Int,
    val records: Int
): Serializable

data class Links(
    val self: String,
    val current: String,
    val next: String,
    val last: String
): Serializable
