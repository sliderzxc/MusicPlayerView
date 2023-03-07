package com.sliderzxc.library.data.entities

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioFile(
    val id: Long,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Int,
    val iconUri: Uri
): Parcelable