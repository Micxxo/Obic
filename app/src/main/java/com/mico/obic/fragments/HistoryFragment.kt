package com.mico.obic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mico.obic.R
import com.mico.obic.fragments.adapters.historyAdapter
import com.mico.obic.models.historyModel
import java.util.Objects


class HistoryFragment : Fragment() {
    private lateinit var db: DatabaseReference
    lateinit var rv: RecyclerView
    lateinit var historyList: ArrayList<historyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        historyList = arrayListOf<historyModel>()
        rv = view.findViewById(R.id.rvHistory)

        if (activity?.intent!!.hasExtra("uuID")) {
            val uuID = activity?.intent!!.getStringExtra("uuID")
            db = uuID?.let { FirebaseDatabase.getInstance().getReference("history").child(it) }!!

            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (historySnap in snapshot.children) {
                            val historyData = historySnap.getValue(historyModel::class.java)
                            historyList.add(historyData!!)
                        }

                        val adapterHistory = historyAdapter(historyList, activity)
                        val lm = LinearLayoutManager(activity)
                        rv.setHasFixedSize(true)
                        rv.layoutManager = lm
                        rv.adapter = adapterHistory
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        return view
    }

}