package com.example.wearsinema.Internet.ModelsChat


import com.google.gson.annotations.SerializedName

data class ModelChat(
    @SerializedName("chatlists")
    val chatlists: List<Chatlists>,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("usermovies")
    val usermovies: Usermovies
)