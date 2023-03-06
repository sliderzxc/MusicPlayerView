package com.main.musicplayerview.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.main.musicplayerview.domain.repository.AudioRepository
import com.sliderzxc.library.data.entities.AudioFile

class MainViewModel(
    private val audioRepository: AudioRepository
) : ViewModel() {

    fun getAllAudioFiles(context: Context) : List<AudioFile> {
        return audioRepository.getAllAudioFiles(context)
    }
}