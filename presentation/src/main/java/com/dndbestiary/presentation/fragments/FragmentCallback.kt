package com.dndbestiary.presentation.fragments

import com.domain.models.DomainPotion

interface FragmentCallback {
    fun sendCallback(callback: String, potion: DomainPotion?): Boolean
}