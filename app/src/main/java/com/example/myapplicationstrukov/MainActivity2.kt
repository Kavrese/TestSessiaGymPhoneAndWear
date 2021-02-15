package com.example.myapplicationstrukov

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        enter.setOnClickListener {
            if (checkEditText(email) && checkEditText(password)) {
                if ("@" in email.text.toString()) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://cinema.areas.su")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    retrofit.create(Api::class.java)
                        .login(email.text.toString(), password.text.toString())
                        .enqueue(object : Callback<ModelToken> {
                            override fun onFailure(call: Call<ModelToken>, t: Throwable) {
                                showDialog(t.message.toString())
                            }

                            override fun onResponse(
                                call: Call<ModelToken>,
                                response: Response<ModelToken>
                            ) {
                                if (response.code() == 200) {
                                    Toast.makeText(this@MainActivity2, "GOOD", Toast.LENGTH_LONG)
                                        .show()
                                }else {
                                    if (response.code() == 400) {
                                        Toast.makeText(this@MainActivity2, "BAD", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                            }
                        })
                } else {
                    Toast.makeText(this@MainActivity2, "Email @", Toast.LENGTH_LONG).show()
                }
            }
        }

        reg.setOnClickListener {
            startActivity(Intent(this, MainActivity2Reg::class.java))
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

    private fun showDialog(title: String){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setNegativeButton("Закрыть"
        ) { dialog, which -> dialog!!.dismiss() }
        dialog.show()
    }
}