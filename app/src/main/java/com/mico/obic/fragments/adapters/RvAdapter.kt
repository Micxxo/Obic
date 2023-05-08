package com.mico.obic.fragments.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mico.obic.JadwalModel
import com.mico.obic.R

class RvAdapter(private val jadwalList: ArrayList<JadwalModel>, var context: Activity?): RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val jdlJadwal = view.findViewById<TextView>(R.id.jdlJadwal)
        val waktuJdwl = view.findViewById<TextView>(R.id.waktuJdwl)
        val statusLogo = view.findViewById<ImageView>(R.id.statusLogo)
        val status = view.findViewById<TextView>(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view: View = LayoutInflater.from(parent.context).inflate(R.layout.jadwal_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return jadwalList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.jdlJadwal.text = jadwalList[position].jdlJadwal
        holder.waktuJdwl.text = jadwalList[position].waktuJdwl
        holder.statusLogo.setImageResource(jadwalList[position].statusLogo)
        holder.status.text = jadwalList[position].status

    }

}