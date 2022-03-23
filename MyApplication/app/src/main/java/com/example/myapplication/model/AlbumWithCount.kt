package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class AlbumWithCount(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("songs_count")
    val songsCount: Int,
    @SerializedName("release_date")
    val releaseDate: String
) {
    override fun toString(): String {
        return name
    }
}
