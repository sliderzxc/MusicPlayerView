package com.main.musicplayerview.di.component

import com.main.musicplayerview.di.modules.DataModule
import com.main.musicplayerview.di.modules.DomainModule
import com.main.musicplayerview.di.modules.PresentationModule
import com.main.musicplayerview.presentation.ui.MainActivity
import com.main.musicplayerview.presentation.ui.MusicActivity
import dagger.Component

@Component(
    modules = [
        PresentationModule::class,
        DomainModule::class,
        DataModule::class
    ]
)
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(musicActivity: MusicActivity)
}