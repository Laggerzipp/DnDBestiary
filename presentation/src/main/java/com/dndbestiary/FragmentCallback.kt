package com.dndbestiary

import com.hfad.data.retrofit.Potion

interface FragmentCallback {
    fun sendCallback(callback: String, potion: Potion?) :Boolean
}