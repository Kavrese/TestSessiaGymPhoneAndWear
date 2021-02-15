package com.example.myapplicationstrukov

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface Api {
    @POST("/auth/register")
    fun reg(@Query("email") email:String, @Query("password") password:String,  @Query("firstName") firstName:String, @Query("lastName") lastName:String): Call<ModelToken>

    @POST("/auth/login")
    fun login(@Query("email") email:String, @Query("password") password: String): Call<ModelToken>

//    @GET("/user/auth/")
//    fun auth(@Query email: String): Call<ModelToken>
}