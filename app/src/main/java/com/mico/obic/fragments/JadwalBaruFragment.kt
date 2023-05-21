package com.mico.obic.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mico.obic.JadwalModel
import com.mico.obic.R
import com.mico.obic.databinding.FragmentJadwalbaruBinding
import com.mico.obic.models.Jadwal
import kotlinx.android.synthetic.main.fragment_jadwalbaru.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*


class JadwalBaruFragment : Fragment() {
    lateinit var jam: ImageView
    lateinit var namaJadwal: EditText
    lateinit var getar: CheckBox
    lateinit var btnSubmit: Button
    private lateinit var database: DatabaseReference
    lateinit var picker: MaterialTimePicker
    var calendar = Calendar.getInstance()
    lateinit var waktu: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jadwalbaru, container, false)

        jam = view.findViewById(R.id.clock)
        waktu = view.findViewById(R.id.waktuJadwal)
        namaJadwal = view.findViewById(R.id.namaJadwal)
        btnSubmit = view.findViewById(R.id.btnTambah)
        val currentDate = Date().toString()

        database = FirebaseDatabase.getInstance().getReference("Jadwal")

        btnSubmit.setOnClickListener {
            if (activity?.intent!!.hasExtra("uuID")) {
                val id = activity?.intent!!.getStringExtra("uuID")
                val nama = namaJadwal.text.toString()
                val waktuJadwal = waktu.text.toString()
                val jadwal = JadwalModel(nama, waktuJadwal, "menunggu", currentDate)
                if (id  != null) {
                    database.child(id).child(waktuJadwal).setValue(jadwal)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Data Inserted Succesfully", Toast.LENGTH_LONG).show()
                        }
                    } else {
                    Toast.makeText(requireContext(), "Failed Insert Data ", Toast.LENGTH_LONG).show()
                }
                }
            }




        jam.setOnClickListener {

            picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Time")
                .build()

            picker.show(childFragmentManager, "Obic")

            picker.addOnPositiveButtonClickListener{
                if (picker.hour > 12) {
                    val PM = String.format("%02d", picker.hour - 12) + ":" + String.format("%02d", picker.minute) + " PM"
                    waktu.setText(PM)
                } else {
                    val AM = String.format("%02d", picker.hour) + ":" + String.format("%02d", picker.minute) + " AM"
                    waktu.setText(AM)
                }

                calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = picker.hour
                calendar[Calendar.MINUTE] = picker.minute
                calendar[Calendar.SECOND] + 0
                calendar[Calendar.MILLISECOND] = 0
            }
        }


        return view
    }




}