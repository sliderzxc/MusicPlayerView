package com.main.musicplayerview.domain.repository

import android.content.Context
import com.sliderzxc.library.data.entities.AudioFile

interface AudioRepository {

    fun getAllAudioFiles(context: Context) : List<AudioFile>
}