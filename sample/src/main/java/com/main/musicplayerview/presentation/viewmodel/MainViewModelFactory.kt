package com.main.musicplayerview.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.main.musicplayerview.domain.repository.AudioRepository

class MainViewModelFactory(
    private val audioRepository: AudioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            audioRepository = audioRepository
        ) as T
    }
}