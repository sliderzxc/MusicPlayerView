package com.main.musicplayerview.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.main.musicplayerview.R
import com.main.musicplayerview.databinding.ItemAudioFileBinding
import com.main.musicplayerview.domain.mapper.AudioFilesMapper
import com.sliderzxc.library.data.entities.AudioFile

interface AudioFilesClickListener {
    fun click(audioFile: AudioFile)
}

class AudioFilesAdapter(
    private val audioFilesClickListener: AudioFilesClickListener
) : RecyclerView.Adapter<AudioFilesAdapter.AudioFilesViewHolder>(), AudioFilesMapper {
    private val audioFiles = mutableListOf<AudioFile>()

    class AudioFilesViewHolder(view: View): ViewHolder(view) {
        private val binding by lazy { ItemAudioFileBinding.bind(view) }

        fun bind(
            audioFile: AudioFile, audioFilesMapper: AudioFilesMapper,
            audioFilesClickListener: AudioFilesClickListener
        ) {
            binding.tvTitle.text = audioFilesMapper.mapTextLength(20, audioFile.title)
            binding.tvArtist.text = audioFilesMapper.mapTextLength(20, audioFile.artist)
            binding.itemLayout.setOnClickListener { audioFilesClickListener.click(audioFile) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioFilesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_audio_file, parent, false)
        return AudioFilesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AudioFilesViewHolder, position: Int) {
        holder.bind(audioFiles[position], this, audioFilesClickListener)
    }

    override fun getItemCount() = audioFiles.size

    @SuppressLint("NotifyDataSetChanged")
    override fun mapAudioFiles(audioFiles: List<AudioFile>) {
        this.audioFiles.addAll(audioFiles)
        notifyDataSetChanged()
    }

    override fun mapTextLength(length: Int, text: String): String {
        return if (text.length > length) "${text.substring(0, length)}..." else text
    }
}