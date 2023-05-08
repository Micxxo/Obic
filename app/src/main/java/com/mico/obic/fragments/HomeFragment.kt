package com.mico.obic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mico.obic.JadwalModel
import com.mico.obic.R
import com.mico.obic.databinding.FragmentHomeBinding
import com.mico.obic.fragments.adapters.RvAdapter


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    lateinit var rvJadwal: RecyclerView

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL

        rvJadwal = view.findViewById(R.id.recylerView)
        val adapterJadwal = RvAdapter(ArrayJadwal, activity)
        rvJadwal.setHasFixedSize(true)
        rvJadwal.layoutManager = lm
        rvJadwal.adapter = adapterJadwal

        return view
    }

    val ArrayJadwal : ArrayList<JadwalModel>get(){
        val arrayjadwal = ArrayList<JadwalModel>()
        val jadwal1 = JadwalModel()
        jadwal1.jdlJadwal = "Main Valo"
        jadwal1.waktuJdwl = "13:40"
        jadwal1.statusLogo = R.drawable.menunggu
        jadwal1.status = "Menunggu"

        val jadwal2 = JadwalModel()
        jadwal2.jdlJadwal = "Kerjain Tugas"
        jadwal2.waktuJdwl = "15:40"
        jadwal2.statusLogo = R.drawable.menunggu
        jadwal2.status = "Menunggu"

        val jadwal3 = JadwalModel()
        jadwal3.jdlJadwal = "Ngabuburit"
        jadwal3.waktuJdwl = "16:40"
        jadwal3.statusLogo = R.drawable.menunggu
        jadwal3.status = "Menunggu"

        val jadwal4 = JadwalModel()
        jadwal4.jdlJadwal = "Meeting malam"
        jadwal4.waktuJdwl = "19:40"
        jadwal4.statusLogo = R.drawable.menunggu
        jadwal4.status = "Menunggu"

        arrayjadwal.add(jadwal1)
        arrayjadwal.add(jadwal2)
        arrayjadwal.add(jadwal3)
        arrayjadwal.add(jadwal4)

        return arrayjadwal
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}