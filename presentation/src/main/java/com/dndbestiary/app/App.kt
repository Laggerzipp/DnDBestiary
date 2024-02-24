package com.dndbestiary.app

import android.app.Application
import com.dndbestiary.di.AppComponent
import com.dndbestiary.di.DaggerAppComponent
import com.dndbestiary.di.PresentationModule

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .presentationModule(PresentationModule(this))
            .build()
    }
}