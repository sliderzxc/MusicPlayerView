package com.main.musicplayerview.app

import android.app.Application
import com.main.musicplayerview.di.component.DaggerMainComponent
import com.main.musicplayerview.di.modules.DataModule
import com.main.musicplayerview.di.modules.DomainModule
import com.main.musicplayerview.di.modules.PresentationModule

class Application : Application() {

    val mainComponent by lazy {
        DaggerMainComponent
            .builder()
            .presentationModule(PresentationModule())
            .domainModule(DomainModule())
            .dataModule(DataModule())
            .build()
    }

}