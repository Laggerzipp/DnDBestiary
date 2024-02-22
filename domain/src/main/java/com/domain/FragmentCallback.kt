package com.domain

import com.domain.model.DomainPotion

interface FragmentCallback {
    fun sendCallback(callback: String, potion: DomainPotion?) :Boolean
}