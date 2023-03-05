package com.main.musicplayerview.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.musicplayerview.data.entities.AudioFile
import com.main.musicplayerview.domain.repository.AudioRepository

class MainViewModel(
    private val audioRepository: AudioRepository
) : ViewModel() {

    fun getAllAudioFiles(context: Context) : List<AudioFile> {
        return audioRepository.getAllAudioFiles(context)
    }
}