package com.example.wearsinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wearsinema.Internet.Api
import com.example.wearsinema.Internet.Models.ModelFilm
import kotlinx.android.synthetic.main.activity_compilition_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompilationScreen : AppCompatActivity() {
    var filmsList = mutableListOf<ModelFilm>()
    var nowPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compilition_screen)

        getData()
    }

    fun getData(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sinemaapp-b5533.firebaseio.com/response/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Api::class.java).getFilms().enqueue(object:
            Callback<List<ModelFilm>> {
            override fun onFailure(call: Call<List<ModelFilm>>, t: Throwable) {
                showAlertDialog("Ошибка получения данных о фильмах", t.message + call.request().url().toString(), this@CompilationScreen)
            }

            override fun onResponse(call: Call<List<ModelFilm>>, response: Response<List<ModelFilm>>) {
                if (response.body() != null) {
                    filmsList = response.body()!!.toMutableList()
                    rec_film.apply {
                        layoutManager = LinearLayoutManager(this@CompilationScreen, LinearLayoutManager.HORIZONTAL, false)
                        adapter = AdapterFilm(filmsList)
                    }
                }else{
                    showAlertDialog("Ошибка получения данных о фильмах", call.request().url().toString(), this@CompilationScreen)
                }
            }
        })
    }
}