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
import com.sliderzxc.library.data.entities.AudioFile
import com.sliderzxc.library.domain.repository.ManageControl

class MusicPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes), ManageControl {

    private var player: ExoPlayer
    private var binding: MusicPlayerViewBinding
    private var isPause = false

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.music_player_view, this, true)
        binding = MusicPlayerViewBinding.bind(view)
        player = ExoPlayer.Builder(context).build()
        clickListeners()
    }

    override fun play(audioFile: AudioFile) {
        val mediaSource = buildMediaSource(Uri.parse(audioFile.path))
        player.addMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
        isPause = false
    }

    override fun stop() {
        player.stop()
        player.release()
    }

    override fun skipPlusTenSeconds() = player.seekTo(player.currentPosition + 10000L)

    override fun skipMinusTenSeconds() = player.seekTo(player.currentPosition - 10000L)

    override fun continuePlay() {
        player.play()
        binding.btnControlMedia.setImageResource(R.drawable.icon_pause)
        isPause = false
    }

    override fun pause() {
        player.pause()
        binding.btnControlMedia.setImageResource(R.drawable.icon_play)
        isPause = true
    }

    private fun clickListeners() {
        btnControlClickListener()
        btnSkipClickListeners()
    }

    private fun btnControlClickListener() {
        binding.btnControlMedia.setOnClickListener {
            if (isPause) {
                continuePlay()
            } else {
                pause()
            }
        }
    }

    private fun btnSkipClickListeners() {
        binding.btnSkipMinusTenSeconds.setOnClickListener { skipMinusTenSeconds() }
        binding.btnSkipPlusTenSeconds.setOnClickListener { skipPlusTenSeconds() }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ProgressiveMediaSource.Factory(
            DefaultDataSourceFactory(context, "custom_user_agent_777")
        ).createMediaSource(MediaItem.fromUri(uri))
    }
}