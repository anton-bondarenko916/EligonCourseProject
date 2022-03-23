package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AlbumItemViewBinding
import com.example.myapplication.model.AlbumWithCount
import com.example.myapplication.model.Albums

interface AlbumActionListener {
    fun onAlbumClicked(albumWithCount: AlbumWithCount)
}

class AlbumsRecyclerViewAdapter(
    private val albums: Albums
): RecyclerView.Adapter<AlbumsRecyclerViewAdapter.AlbumViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = AlbumItemViewBinding.inflate(inflater, parent, false)

        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums.data[position]

        with(holder.binding) {
            holder.itemView.tag = album
            albumNameServer.text = album.name
            tvSongsCountServer.text = album.songsCount.toString()
            tvReleaseDateServer.text = album.releaseDate
        }
    }

    override fun getItemCount(): Int {
        return albums.data.size
    }

    class AlbumViewHolder(
        val  binding: AlbumItemViewBinding
    ): RecyclerView.ViewHolder(binding.root)
}