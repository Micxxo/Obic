package com.mico.obic.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.common.data.DataBuffer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mico.obic.Home
import com.mico.obic.Login
import com.mico.obic.R
import com.mico.obic.models.Users
import kotlinx.android.synthetic.main.activity_sign_up_berhasil.*
import org.w3c.dom.Text
import kotlin.math.log


class ProfilFragment : Fragment() {
    lateinit var logout: TextView
    lateinit var nama: TextView
    lateinit var point: TextView
    lateinit var level: TextView
    lateinit var jdwlDone: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private  lateinit var userList: ArrayList<Users>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        logout = view.findViewById(R.id.logout)
        nama = view.findViewById(R.id.nama)
        point = view.findViewById(R.id.point)
        level = view.findViewById(R.id.lvlDisiplin)
        jdwlDone = view.findViewById(R.id.jadwalDone)
        auth = FirebaseAuth.getInstance()
        getData()


        logout.setOnClickListener{
            auth.signOut()
            val i = Intent(requireContext(), Login::class.java)
            startActivity(i)
        }

        return view
    }

    private fun getData() {
        database = FirebaseDatabase.getInstance().getReference("Users")

        if (activity?.intent!!.hasExtra("uuID")) {
            val id = activity?.intent!!.getStringExtra("uuID")
            if (id != null) {
                database.child(id).get().addOnSuccessListener {
                    if (it.exists()) {
                        val email = it.child("email").value
                        nama.text = email.toString()

                        val points = it.child("point").value
                        point.text = points.toString() + " Point"

                        val lvl = it.child("level").value
                        level.text = lvl.toString()

                        val jdwlSelesai = it.child("jdwlDone").value
                        jdwlDone.text = jdwlSelesai.toString() + " Jadwal"

                }
            }


        }


        }
    }


}