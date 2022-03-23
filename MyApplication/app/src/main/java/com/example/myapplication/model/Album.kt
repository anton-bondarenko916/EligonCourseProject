package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("songs")
    val songs: MutableList<Song>,

    @SerializedName("release_date")
    val releaseDate: String
)
