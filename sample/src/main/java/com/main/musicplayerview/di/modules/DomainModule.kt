package com.main.musicplayerview.di.modules

import com.main.musicplayerview.data.realization.AudioRepositoryImpl
import com.main.musicplayerview.domain.repository.AudioRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideAudioRepository(): AudioRepository {
        return AudioRepositoryImpl()
    }

}