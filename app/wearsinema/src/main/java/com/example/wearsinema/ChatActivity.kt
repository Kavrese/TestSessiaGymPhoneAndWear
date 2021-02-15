package com.example.wearsinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wearsinema.Internet.Api
import com.example.wearsinema.Internet.ModelsChat.ModelChat
import kotlinx.android.synthetic.main.activity_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        getData()
    }

    fun getData(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sinemaapp-b5533.firebaseio.com/response/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Api::class.java).getChatList(editEmail(User.user_email!!)).enqueue(object:
            Callback<ModelChat>{
            override fun onFailure(call: Call<ModelChat>, t: Throwable) {
                showAlertDialog("Ошибка получения данных о фильмах", t.message + call.request().url().toString(), this@ChatActivity)
            }

            override fun onResponse(call: Call<ModelChat>, response: Response<ModelChat>) {
                if (response.body() != null) {
                    rec_chat.apply {
                        layoutManager = LinearLayoutManager(this@ChatActivity)
                        adapter = AdapterChat(response.body()!!.chatlists)
                    }
                }else{
                    showAlertDialog("Ошибка получения данных о фильмах", call.request().url().toString(), this@ChatActivity)
                }
            }

        })
    }

    fun editEmail(email: String): String{
        val one = email.substringBefore("@")
        val two = email.substringAfter("@").substringBefore(".")
        return one + "_" + two + "_" + "com"
    }
}