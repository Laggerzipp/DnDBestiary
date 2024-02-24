package com.dndbestiary.di

import com.dndbestiary.presentation.MainActivity
import dagger.Component

@Component(modules = [PresentationModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}