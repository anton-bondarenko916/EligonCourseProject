package com.example.myapplication.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.model.AlbumWithCount
import com.example.myapplication.model.Albums
import com.github.javafaker.Faker
import org.apache.commons.lang3.RandomUtils.nextInt

class AlbumsFragment : Fragment() {

    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var mRefresher: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_albums, container, false)
        albumsRecyclerView = view.findViewById(R.id.albums_recycler_view)
        albumsRecyclerView.layoutManager = LinearLayoutManager(context)

        var adapter = AlbumsRecyclerViewAdapter(getAlbums())
        albumsRecyclerView.adapter = adapter
        mRefresher = view.findViewById(R.id.swipe_refresh_albums)
        mRefresher.setOnRefreshListener { refreshListOfAlbums() }

        return view
    }

    private fun getAlbums(): Albums {
        val faker = Faker.instance()
        val list = mutableListOf<AlbumWithCount>()
        for (i in 0..99) {
            list.add(
                AlbumWithCount(
                    i,
                    faker.animal().name(),
                    nextInt(5, 20),
                    "${nextInt(1, 31)}.${nextInt(1, 12)}.${nextInt(1950, 2022)}"))
        }
        return Albums(list)
    }

    private fun refreshListOfAlbums() {
        mRefresher.isRefreshing = true
        val adapter = AlbumsRecyclerViewAdapter(getAlbums())
        albumsRecyclerView.adapter = adapter
        mRefresher.isRefreshing = false
    }

    companion object {
        fun newInstance() = AlbumsFragment()
    }
}