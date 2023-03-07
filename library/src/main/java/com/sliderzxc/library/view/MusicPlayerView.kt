package com.sliderzxc.library.view

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.main.library.R
import com.main.library.databinding.MusicPlayerViewBinding
import com.sliderzxc.library.data.entities.AudioFile
import com.sliderzxc.library.domain.Init
import com.sliderzxc.library.domain.ManageControl

class MusicPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes), ManageControl, Init {

    private var player: ExoPlayer
    private var binding: MusicPlayerViewBinding
    private var isPause = false
    private lateinit var handler: Handler

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.music_player_view, this, true)
        binding = MusicPlayerViewBinding.bind(view)
        player = ExoPlayer.Builder(context).build()
        clickListeners()
        initHandler()
    }

    override fun initSeekBar(duration: Int) {
        binding.sbSongProgress.max = duration
        binding.tvMaxTime.text = formatTime(duration)

        binding.sbSongProgress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                player.seekTo(progress.toLong())
                binding.tvCurrentTime.text = formatTime(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    override fun initHandler() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                val currentPosition = player.currentPosition.toInt()
                binding.sbSongProgress.progress = currentPosition
                binding.tvCurrentTime.text = formatTime(currentPosition)
                handler.postDelayed(this, 1000)
            }
        })
    }
    private fun formatTime(millis: Int): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun play(audioFile: AudioFile) {
        Log.d("MyLog", "audioFile: $audioFile")
        val mediaSource = buildMediaSource(Uri.parse(audioFile.path))
        player.addMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
        isPause = false
        Glide.with(context).load(audioFile.iconUri).into(binding.ivSongIcon)
        initSeekBar(audioFile.duration)
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