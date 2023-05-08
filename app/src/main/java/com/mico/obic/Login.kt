package com.mico.obic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        val signUp: Button = findViewById(R.id.btnSignUp)
        val login: Button = findViewById(R.id.btnLogin)

        login.setOnClickListener {
            val i = Intent(this@Login, Home::class.java)
            startActivity(i)
        }

        signUp.setOnClickListener {
            val i = Intent(this@Login, Register_Page::class.java)
            startActivity(i)
        }

    }
}