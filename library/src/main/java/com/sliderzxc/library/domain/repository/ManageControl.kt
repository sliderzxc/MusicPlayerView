package com.sliderzxc.library.domain.repository

import android.net.Uri

interface ManageControl {

    fun play(mediaUri: Uri)

    fun stop()

    fun continuePlay()

    fun pause()
}