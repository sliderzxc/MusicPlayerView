package com.main.musicplayerview.presentation.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.main.musicplayerview.app.Application
import com.main.musicplayerview.data.Constants
import com.main.musicplayerview.databinding.ActivityMusicBinding
import com.sliderzxc.library.data.entities.AudioFile

class MusicActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMusicBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        (applicationContext as Application).mainComponent.inject(this)

        val audioFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.KEY_AUDIO_FILE, AudioFile::class.java)
        } else {
            intent.getParcelableExtra(Constants.KEY_AUDIO_FILE)
        }
        Log.d("MyLog", "audioFile: $audioFile")
        audioFile?.let { binding.musicPlayerView.play(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.musicPlayerView.stop()
    }
}