package com.main.musicplayerview.domain.mapper

import com.main.musicplayerview.data.entities.AudioFile

interface AudioFilesMapper {

    fun mapAudioFiles(audioFiles: List<AudioFile>)

    fun mapTextLength(length: Int, text: String): String
}