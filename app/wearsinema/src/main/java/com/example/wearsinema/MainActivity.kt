package com.example.wearsinema

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.EditText
import android.widget.Toast
import com.example.wearsinema.Internet.Api
import com.example.wearsinema.Internet.Models.ModelToken
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        enter.setOnClickListener {
            if (checkEditText(email) && checkEditText(password)) {
                if ("@" in email.text.toString()) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://cinema.areas.su/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    retrofit.create(Api::class.java)
                        .login(email.text.toString(), password.text.toString())
                        .enqueue(object : Callback<ModelToken> {
                            override fun onFailure(call: Call<ModelToken>, t: Throwable) {
                                showAlertDialog("Ошибка авторизации",t.message.toString() + call.request().url(), this@MainActivity)
                            }

                            override fun onResponse(
                                call: Call<ModelToken>,
                                response: Response<ModelToken>
                            ) {
                                if (response.code() == 200) {
                                    User.user_email = email.text.toString()
                                    startActivity(Intent(this@MainActivity, MainScreen::class.java))
                                    finish()
                                }
                                if (response.code() == 400) {
                                    Toast.makeText(this@MainActivity, "BAD", Toast.LENGTH_LONG)
                                            .show()
                                }
                                if (response.code() == 401){
                                    showAlertDialog("Ошибка входа", "Unauthorized", this@MainActivity)
                                }
                            }
                        })
                } else {
                    Toast.makeText(this@MainActivity, "Email @", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkEditText (v: EditText): Boolean{
        return if (v.text.isNotEmpty()){
            true
        }else{
            Toast.makeText(this, "Не заполненные поля", Toast.LENGTH_LONG).show()
            false
        }
    }
}

fun showAlertDialog(title: String, mes: String, context: Context){
    val dialog = AlertDialog.Builder(context)
    dialog.setTitle(title)
    dialog.setMessage(mes)
    dialog.setNegativeButton("Закрыть"
    ) { dialog, which -> dialog!!.dismiss() }
    dialog.show()
}