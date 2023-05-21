package com.mico.obic.fragments

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.renderscript.Sampler.Value
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.values
import com.mico.obic.AlarmReceiver
import com.mico.obic.JadwalModel
import com.mico.obic.R
import com.mico.obic.Verify
import com.mico.obic.databinding.FragmentHomeBinding
import com.mico.obic.fragments.adapters.RvAdapter
import com.mico.obic.models.waktuAlarm
import kotlinx.android.synthetic.main.activity_verify.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.w3c.dom.Text
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    lateinit var rvJadwal: RecyclerView
    lateinit var btnTes: Button
    lateinit var loading: TextView
    lateinit var tanggal: TextView
    lateinit var hari: TextView
    lateinit var emailUser: TextView
    private lateinit var database: DatabaseReference
    private lateinit var dbForDelete: DatabaseReference
    private lateinit var jadwalList: ArrayList<JadwalModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        jadwalList = arrayListOf<JadwalModel>()
        rvJadwal = view.findViewById(R.id.recylerView)
        loading = view.findViewById(R.id.loading)
        tanggal = view.findViewById(R.id.tanggal)
        hari = view.findViewById(R.id.hari)
        emailUser = view.findViewById(R.id.nama)

        loading.visibility = View.GONE

        val calendar = Calendar.getInstance()

        //waktu
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formattedTime = currentTime.format(formatter)

        //tanggal
        val currentDate: LocalDate = LocalDate.now()
        val formatterDate: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate: String = currentDate.format(formatterDate)

        //Hari
        val currentDay: LocalDate = LocalDate.now()
        val dayOfWeek: DayOfWeek = currentDay.dayOfWeek
        val dayOfWeekString: String = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

        hari.text = dayOfWeekString
        tanggal.text = formattedDate
        if (activity?.intent!!.hasExtra("email")) {
            val email = activity?.intent!!.getStringExtra("email")
            emailUser.text = email
        }

        if (activity?.intent!!.hasExtra("uuID")) {
            val uuID = activity?.intent!!.getStringExtra("uuID")
            database = uuID?.let { FirebaseDatabase.getInstance().getReference("Jadwal").child(it) }!!
            dbForDelete = uuID?.let { FirebaseDatabase.getInstance().getReference("Jadwal").child(it).child(formattedTime)}!!

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    loading.visibility = View.VISIBLE
                    rvJadwal.visibility = View.GONE
                    if (snapshot.exists()) {
                        loading.visibility = View.GONE
                        rvJadwal.visibility = View.VISIBLE

                        for (jdwlSnap in snapshot.children) {
                            val jadwalData = jdwlSnap.getValue(JadwalModel::class.java)
                            jadwalList.add(jadwalData!!)

                        }

                        val lm = LinearLayoutManager(activity)
                        lm.orientation = LinearLayoutManager.VERTICAL

                        val adapterJadwal = RvAdapter(jadwalList, activity)
                        rvJadwal.setHasFixedSize(true)
                        rvJadwal.layoutManager = lm
                        rvJadwal.adapter = adapterJadwal

                        adapterJadwal.setOnItemClickListener(object : RvAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                var pos = position
                                val i = Intent(activity, Verify::class.java)
                                i.putExtra("jdl", jadwalList[pos].namaJadwal)
                                i.putExtra("waktu", jadwalList[pos].waktuJawal)
                                i.putExtra("status", jadwalList[pos].status)
                                i.putExtra("createdAt", jadwalList[pos].createdAt)
                                i.putExtra("uuID", uuID)

                                startActivity(i)
                            }
                        })
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}