package com.sliderzxc.library.domain

import com.sliderzxc.library.data.entities.AudioFile

interface ManageControl {

    fun play(audioFile: AudioFile)

    fun stop()

    fun continuePlay()

    fun pause()

    fun skipPlusTenSeconds()

    fun skipMinusTenSeconds()
}