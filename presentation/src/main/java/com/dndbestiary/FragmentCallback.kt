package com.dndbestiary

import com.domain.DomainPotion

interface FragmentCallback {
    fun sendCallback(callback: String, potion: DomainPotion?) :Boolean
}