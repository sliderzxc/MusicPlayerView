package com.main.musicplayerview.presentation.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.main.musicplayerview.app.Application
import com.main.musicplayerview.data.Constants
import com.main.musicplayerview.data.entities.AudioFile
import com.main.musicplayerview.databinding.ActivityMainBinding
import com.main.musicplayerview.presentation.adapter.AudioFilesAdapter
import com.main.musicplayerview.presentation.adapter.AudioFilesClickListener
import com.main.musicplayerview.presentation.viewmodel.MainViewModel
import com.main.musicplayerview.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { mainViewModelFactory }

    private val audioFilesClickListener = object : AudioFilesClickListener {
        override fun click(audioFile: AudioFile) {
            val intent = Intent(applicationContext, MusicActivity::class.java)
            intent.putExtra(Constants.KEY_AUDIO_FILE, audioFile)
            startActivity(intent)
        }
    }
    private val audioFilesAdapter = AudioFilesAdapter(audioFilesClickListener)

    private val multiplePermissionsListener = object : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            audioFilesAdapter.mapAudioFiles(mainViewModel.getAllAudioFiles(applicationContext))
        }

        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?
        ) = requestPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        (applicationContext as Application).mainComponent.inject(this)

        binding.rvSongs.adapter = audioFilesAdapter
        requestPermission()
        //audioFilesAdapter.mapAudioFiles(mainViewModel.getAllAudioFiles(applicationContext))
    }

    private fun requestPermission() {
        Dexter.withContext(applicationContext)
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(multiplePermissionsListener)
            .check()
    }
}