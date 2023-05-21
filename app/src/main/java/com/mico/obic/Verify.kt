package com.mico.obic

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.mico.obic.fragments.HistoryFragment
import com.mico.obic.fragments.adapters.RvAdapter
import com.mico.obic.models.Users
import com.mico.obic.models.historyModel
import com.mico.obic.models.updateUser
import kotlinx.android.synthetic.main.fragment_jadwalbaru.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

class Verify : AppCompatActivity() {
    lateinit var jdwl: TextView
    lateinit var btnVerif: Button
    lateinit var email: String
    lateinit var pass: String
    var point = 0
    var jdwlDone = 0
    var level = 0
    private lateinit var db: DatabaseReference
    private lateinit var dbDelete: DatabaseReference
    private lateinit var dbUser: DatabaseReference
    private lateinit var dbCheckTime: DatabaseReference
    private lateinit var updateData: ArrayList<updateUser>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        supportActionBar?.hide()

        btnVerif = findViewById(R.id.btnVerifikasi)
        jdwl = findViewById(R.id.jadwal)
        updateData = arrayListOf<updateUser>()

        val jdlJadwal = intent.getStringExtra("jdl")
        val waktu = intent.getStringExtra("waktu")
        val uuID = intent.getStringExtra("uuID")

        //waktu
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formattedTime = currentTime.format(formatter)

        if (jdlJadwal != null) {
            db = uuID?.let {
                FirebaseDatabase.getInstance().getReference("history").child(it).child(jdlJadwal)
            }!!
        }
        if (waktu != null) {
            dbDelete = uuID?.let {
                FirebaseDatabase.getInstance().getReference("Jadwal").child(it).child(waktu)
            }!!
        }

        dbCheckTime = FirebaseDatabase.getInstance().getReference("Jadwal").child(formattedTime)


        jdwl.text = "Verifikasi setelah anda menyelesaikan ${jdlJadwal}!"

        btnVerif.setOnClickListener {
            toHistory()
            updateProfil()
            dbDelete.removeValue().addOnSuccessListener {
                Toast.makeText(applicationContext, "hapus Berhasil", Toast.LENGTH_LONG).show()
            }

            var mp = MediaPlayer.create(applicationContext, R.raw.alarmringing)
            mp.stop()

            val i = Intent(applicationContext, Home::class.java)
            i.putExtra("uuID", uuID)
            startActivity(i)

        }
    }

    private fun toHistory() {
        val jdlJadwal = intent.getStringExtra("jdl")
        val waktu = intent.getStringExtra("waktu")
        val status = "selesai"
        val createdAt = intent.getStringExtra("createdAt")
        val getar = intent.getBooleanExtra("isGetar", false)

        var history = historyModel(jdlJadwal, waktu, getar, status, createdAt)
        db.setValue(history)
    }

    private fun updateProfil() {
        val uuID = intent.getStringExtra("uuID")
        dbUser = FirebaseDatabase.getInstance().getReference("Users")

        if (uuID != null) {
            dbUser.child(uuID).get().addOnSuccessListener {
                if (it.exists()) {
                    val email = it.child("email").value.toString()

                    val pass = it.child("password").value.toString()

                    val points = it.child("point").value.toString().toInt()


                    val lvl = it.child("level").value.toString().toInt()

                    val jdwlSelesai = it.child("jdwlDone").value.toString().toInt()

                    var lvlTotal = 0
                    if (points in 0..10) {
                        lvlTotal = 0
                    }  else if (points in 10..50) {
                        lvlTotal = 1
                    } else if (points in 50..100) {
                        lvlTotal = 2
                    } else if (points in 100..150) {
                        lvlTotal = 3
                    } else if (points in 150..200) {
                        lvlTotal = 4
                    } else if (points in 200..250) {
                        lvlTotal = 5
                    }

                    val update = updateUser(email, pass, uuID, lvlTotal,points + 10 ,jdwlSelesai + 1)
                    dbUser.child(uuID).setValue(update)
                }
            }




        }

        }
}

//override fun onDataChange(snapshot: DataSnapshot) {



    //                if (snapshot.exists()) {

//                    for(snapData in snapshot.children){
//                            val data = snapData.getValue(updateUser::class.java)
//                            updateData.add(data!!)
//                    }
//
//                    for (data in updateData) {
//                        email = data.email.toString()
//                        pass = data.password.toString()
//                        point = data.point!!
//                        jdwlDone = data.jdwlDone!!
//                        level = data.level!!
//                    }
//
//                    Toast.makeText(applicationContext, "ini adalah ${email}", Toast.LENGTH_LONG).show()




//                    }


//override fun onCancelled(error: DatabaseError) {
//    TODO("Not yet implemented")
//}

