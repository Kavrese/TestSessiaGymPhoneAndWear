package com.example.myapplicationstrukov

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_activity2_reg.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2Reg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity2_reg)

        back.setOnClickListener {
            finish()
        }

        reg.setOnClickListener {
            if (checkEditText(email) && checkEditText(password) && checkEditText(password2) && checkEditText(lastName) && checkEditText(firstName)) {
                if ("@" in email.text.toString()) {
                    if (password.text.toString() == password2.text.toString()) {
                        val retrofit = Retrofit.Builder()
                            .baseUrl("http://cinema.areas.su/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        retrofit.create(Api::class.java)
                            .reg(email.text.toString(), password.text.toString(), firstName.text.toString(), lastName.text.toString())
                            .enqueue(object : Callback<ModelToken> {
                                override fun onFailure(call: Call<ModelToken>, t: Throwable) {
                                    if (t.message == "Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"){
                                        startActivity(Intent(this@MainActivity2Reg, MainActivity3::class.java))
                                    }else{
                                        showDialog(t.message.toString())
                                    }
                                }

                                override fun onResponse(
                                    call: Call<ModelToken>,
                                    response: Response<ModelToken>
                                ) {
                                    if (response.code() == 200) {
                                        startActivity(Intent(this@MainActivity2Reg, MainActivity3::class.java))
                                    }
                                    if (response.code() == 400) {
                                        Toast.makeText(
                                            this@MainActivity2Reg,
                                            "BAD",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                }
                            })
                    }else{
                        Toast.makeText(this@MainActivity2Reg, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity2Reg, "Email @", Toast.LENGTH_LONG).show()
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

    private fun showDialog(title: String){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setNegativeButton("Закрыть"
        ) { dialog, which -> dialog!!.dismiss() }
        dialog.show()
    }
}