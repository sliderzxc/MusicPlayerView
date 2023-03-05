package com.sliderzxc.musicplayerview

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.main.musicplayerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val multiplePermissionsListener = object : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            binding.musicPlayerView.play(Uri.EMPTY)
        }

        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?,
        ) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Dexter.withContext(applicationContext)
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(multiplePermissionsListener)
            .check()

        val uri = Uri.parse(getAllAudioFiles(applicationContext).random().path)
        binding.musicPlayerView.play(uri)
    }
}

fun getAllAudioFiles(context: Context): List<AudioFile> {
    val audioFiles = mutableListOf<AudioFile>()
    val contentResolver = context.contentResolver
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA
    )
    val cursor = contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val title = cursor.getString(titleColumn)
            val artist = cursor.getString(artistColumn)
            val path = cursor.getString(dataColumn)
            val audioFile = AudioFile(id, title, artist, path)
            audioFiles.add(audioFile)
        }
    }
    return audioFiles
}

data class AudioFile(
    val id: Long,
    val title: String,
    val artist: String,
    val path: String
)