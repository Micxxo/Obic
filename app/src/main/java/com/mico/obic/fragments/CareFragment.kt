package com.mico.obic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mico.obic.R
import com.mico.obic.models.obicaresModel
import kotlinx.android.synthetic.main.fragment_care.*


class CareFragment : Fragment() {
    lateinit var masalah: EditText
    lateinit var desc: EditText
    lateinit var posting: Button
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_care, container, false)
        val id = activity?.intent!!.getStringExtra("uuID")

        masalah = view.findViewById(R.id.masalahMu)
        desc = view.findViewById(R.id.deskripsiMasalah)
        posting = view.findViewById(R.id.btnPosting)
        db = FirebaseDatabase.getInstance().getReference("obicares")

        posting.setOnClickListener {
            if (id != null) {
                val dataMasalah = masalah.text.toString()
                val descMasalah = desc.text.toString()
                val data = obicaresModel(dataMasalah, descMasalah)
                db.child(id).child(dataMasalah).setValue(data).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Berhasil mengirim data!", Toast.LENGTH_LONG).show()
                }
            }
        }

        return view

    }

}