package com.main.musicplayerview.presentation.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.main.musicplayerview.app.Application
import com.main.musicplayerview.data.Constants
import com.main.musicplayerview.data.entities.AudioFile
import com.main.musicplayerview.databinding.ActivityMusicBinding

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
        binding.musicPlayerView.play(Uri.parse(audioFile?.path))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.musicPlayerView.stop()
    }
}