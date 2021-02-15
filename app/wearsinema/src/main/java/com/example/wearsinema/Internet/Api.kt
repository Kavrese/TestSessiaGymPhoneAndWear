package com.example.wearsinema.Internet

import com.example.wearsinema.Internet.Models.ModelFilm
import com.example.wearsinema.Internet.Models.ModelToken
import com.example.wearsinema.Internet.ModelsChat.ModelChat
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("auth/login")
    fun login(@Query("email") email:String, @Query("password") password: String): Call<ModelToken>

    @GET("userlists/{email}.json")
    fun getChatList(@Path("email") email: String): Call<ModelChat>

    @GET("movieslist.json")
    fun getFilms(): Call<List<ModelFilm>>
}