package com.sliderzxc.library.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.main.library.R
import com.main.library.databinding.MusicPlayerViewBinding

class MusicPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var player: ExoPlayer
    private var binding: MusicPlayerViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.music_player_view, this, true)
        binding = MusicPlayerViewBinding.bind(view)
        player = ExoPlayer.Builder(context).build()
    }

    fun play(mediaUri: Uri) {
        val mediaSource = buildMediaSource(mediaUri)
        player.addMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
    }

    fun stop() {
        player.stop()
        player.release()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ProgressiveMediaSource.Factory(
            DefaultDataSourceFactory(context, "custom_user_agent_777")
        ).createMediaSource(MediaItem.fromUri(uri))
    }
}