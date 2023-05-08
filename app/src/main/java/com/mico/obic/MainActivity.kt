package com.mico.obic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        Handler().postDelayed({
            val i = Intent(this@MainActivity, Login::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}