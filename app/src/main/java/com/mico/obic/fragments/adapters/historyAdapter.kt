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
import com.mico.obic.models.historyModel

class historyAdapter(private val historyList: ArrayList<historyModel>, var context: Activity?): RecyclerView.Adapter<historyAdapter.myViewHolder>() {

// private lateinit var mListener: onItemClickListener
//
// interface onItemClickListener {
//  fun onItemClick(position: Int)
// }
//
// fun setOnItemClickListener(listener: onItemClickListener){
//  mListener = listener
// }


 class myViewHolder(view: View): RecyclerView.ViewHolder(view) {
  val jdlJadwal = view.findViewById<TextView>(R.id.jdlJadwal)
  val waktuJdwl = view.findViewById<TextView>(R.id.waktuJdwl)
  val statusLogo = view.findViewById<ImageView>(R.id.statusLogo)
  val status = view.findViewById<TextView>(R.id.status)

//  init {
//   itemView.setOnClickListener{
//    listener.onItemClick(adapterPosition)
//   }
//  }

 }

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
   val view: View = LayoutInflater.from(parent.context).inflate(R.layout.jadwal_item, parent ,false)
   return myViewHolder(view)
 }

 override fun getItemCount(): Int {
  return historyList.size

 }

 override fun onBindViewHolder(holder: myViewHolder, position: Int) {
  val jadwal = historyList[position]
  holder.jdlJadwal.text = historyList[position].namaJadwal
  holder.waktuJdwl.text = historyList[position].waktuJawal
  holder.status.text = historyList[position].status
 }
}