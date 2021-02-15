package com.example.wearsinema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_screen.*

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        chat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
        multi.setOnClickListener {
            startActivity(Intent(this, CompilationScreen::class.java))
        }
    }
}