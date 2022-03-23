package com.example.myapplication.api

import com.example.myapplication.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AcademyApi {
    @POST("registration/")
    fun registration(@Body user: User): Call<Void>

    @GET("albums/")
    fun getAlbums(): Call<Albums>

    @GET("albums/{id}")
    fun getAlbum(@Path("id") id: Int): Call<Album>

    @GET("songs")
    fun getSongs(): Call<Songs>

    @GET("songs/{id}")
    fun getSong(@Path("id") id: Int): Call<Song>
}