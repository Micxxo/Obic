package com.mico.obic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mico.obic.databinding.ActivityLoginBinding
import com.mico.obic.databinding.ActivityRegisterPageBinding

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if(email.isEmpty()){
                binding.email.error = "Harap Mengisi Email!"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "Email Tidak Valid!"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.password.error = "Harap Mengisi Password!"
                binding.password.requestFocus()
                return@setOnClickListener
            }


            loginFirebase(email, password)

        }

        if (auth.currentUser != null) {
            val uuid = auth.currentUser!!.uid
            val email = auth.currentUser!!.email
            val intent = Intent(this@Login, Home::class.java)
            intent.putExtra("uuID", uuid)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val i = Intent(this@Login, Register_Page::class.java)
            startActivity(i)
        }


    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                val uuid = auth.currentUser!!.uid
                if (it.isSuccessful){
                    Toast.makeText(this,"Login Berhasil ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Login, Home::class.java)
                    intent.putExtra("uuID", uuid)
                    intent.putExtra("email", email)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}