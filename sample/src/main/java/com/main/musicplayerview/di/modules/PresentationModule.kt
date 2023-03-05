package com.main.musicplayerview.di.modules

import com.main.musicplayerview.domain.repository.AudioRepository
import com.main.musicplayerview.presentation.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideMainViewModelFactory(
        audioRepository: AudioRepository
    ): MainViewModelFactory {
        return MainViewModelFactory(
            audioRepository = audioRepository
        )
    }

}