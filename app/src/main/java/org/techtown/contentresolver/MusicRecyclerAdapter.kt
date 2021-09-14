package org.techtown.contentresolver

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.contentresolver.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MusicRecyclerAdapter:RecyclerView.Adapter<MusicRecyclerAdapter.Holder>() {
    var musicList = mutableListOf<Music>()
    var mediaPlayer : MediaPlayer? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        holder.setMusic(music)
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var musicUri: Uri? = null

        init {
            itemView.setOnClickListener {
                if(mediaPlayer != null){    //Holder클래스를 inner클래스로 바꾸고 이처럼 만들어야 노래가 중복해서 안들림.
                     mediaPlayer?.release()
                    mediaPlayer = null
                }
                val mediaPlayer = MediaPlayer.create(itemView.context, musicUri)
                mediaPlayer?.start()
            }
        }

        fun setMusic(music: Music) {
            binding.run {
                imageAlbum.setImageURI(music.getAlbumUri())
                Glide.with(binding.root).load(music.getAlbumUri()).into(imageAlbum)
                textArtist.text = music.artist
                textTitle.text = music.title

                val duration = SimpleDateFormat("mm:ss").format(music.duration)
                textDuration.text = duration
            }
            this.musicUri = music.getMusicUri()
        }

    }
}