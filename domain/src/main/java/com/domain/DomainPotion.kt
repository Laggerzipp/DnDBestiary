package com.domain

import java.io.Serializable

data class DomainPotion(
    val id: String,
    val characteristics: String?,
    val difficulty: String?,
    val effect: String?,
    var image: String?,
    val ingredients: String?,
    val name: String,
    val sideEffects: Any?
): Serializable
