package com.dndbestiary.viewmodel

import androidx.lifecycle.ViewModel
import com.domain.models.DomainPotion

class PotionViewModel(var potion: DomainPotion? = null) : ViewModel()