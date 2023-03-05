package com.main.musicplayerview.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.main.musicplayerview.app.Application
import com.main.musicplayerview.databinding.ActivityMusicBinding
import com.main.musicplayerview.presentation.viewmodel.MainViewModel
import com.main.musicplayerview.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MusicActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMusicBinding.inflate(layoutInflater) }
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { mainViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as Application).mainComponent.inject(this)
    }
}