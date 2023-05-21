package com.mico.obic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mico.obic.databinding.ActivityRegisterPageBinding
import com.mico.obic.models.Users

class Register_Page : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var binding: ActivityRegisterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")


        binding.btnSignUp.setOnClickListener{
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

            if(password != binding.confPassword.text.toString()) {
                binding.confPassword.error = "Password tidak sesuai!"
                binding.confPassword.requestFocus()
                return@setOnClickListener
            }

            if(password.length < 6) {
                binding.password.error = "Password Minumum 6 Karakter!"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            registerFirebase(email, password)

        }

    }
    private fun registerFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val uuId = it.getResult().user?.uid
                    val users = Users(email, password, uuId, 0, 0, 0)
                    if (uuId != null) {
                                database.child(uuId).setValue(users)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Data Inserted Succesfully", Toast.LENGTH_LONG).show()
                            }
                    }else {
                        Toast.makeText(this,"Something Wrong with the DB", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this,"Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Register_Page, Login::class.java)
                    intent.putExtra("uuID", uuId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }
}

