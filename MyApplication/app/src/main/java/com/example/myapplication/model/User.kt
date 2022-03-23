package com.example.myapplication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("email")
    val mLogin: String,
    @SerializedName("name")
    val mName: String,
    @SerializedName("password")
    val mPassword: String
) : Serializable {
}