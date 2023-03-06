package com.main.musicplayerview.data.realization

import android.content.Context
import android.provider.MediaStore
import com.main.musicplayerview.domain.repository.AudioRepository
import com.sliderzxc.library.data.entities.AudioFile

class AudioRepositoryImpl : AudioRepository {

    override fun getAllAudioFiles(context: Context): List<AudioFile> {
        val audioFiles = ArrayList<AudioFile>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val path = cursor.getString(dataColumn)
                val duration = cursor.getInt(durationColumn)

                val audioFile = AudioFile(id, title, artist, path, duration)
                audioFiles.add(audioFile)
            }
        }
        return audioFiles
    }
}