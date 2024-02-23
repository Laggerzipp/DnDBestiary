package com.domain

import com.domain.models.DomainPotion

interface FragmentCallback {
    fun sendCallback(callback: String, potion: DomainPotion?) :Boolean
}