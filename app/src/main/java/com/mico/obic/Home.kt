package com.mico.obic

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.mico.obic.fragments.*
import com.mico.obic.fragments.adapters.ViewPagerAdapter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Home : AppCompatActivity() {
    lateinit var pager: ViewPager
    lateinit var tab: TabLayout
    private val CHANNEL_ID = "Obic Reminder App"
    private val notificationID = 101
    lateinit var btn: Button
    private lateinit var db: DatabaseReference
    private val refreshInterval = 10000L // Refresh interval in milliseconds
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var refreshRunnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val uuID = intent.getStringExtra("uuID")

        refreshRunnable = object : Runnable {
            override fun run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val currentTime = LocalTime.now()
                    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                    val formattedTime = currentTime.format(formatter)
                    db = uuID?.let { FirebaseDatabase.getInstance().getReference("Jadwal").child(it).child(formattedTime) }!!

                    db.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                sendNotification()

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }

                handler.postDelayed(this, refreshInterval)
            }
        }

        supportActionBar?.hide()

        setUpTabs()
        createNotificationChannel()
    }


    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_LONG).show()
        handler.postDelayed(refreshRunnable, refreshInterval)
    }

    override fun onPause() {
        super.onPause()
        val uuID = intent.getStringExtra("uuID")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("hh:mm a")
            val formattedTime = currentTime.format(formatter)
            db = uuID?.let { FirebaseDatabase.getInstance().getReference("Jadwal").child(it).child(formattedTime) }!!

            Toast.makeText(applicationContext, "saat ini pukul: ${formattedTime}", Toast.LENGTH_LONG).show()


            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        sendNotification()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        handler.removeCallbacks(refreshRunnable)
    }

    private fun setUpTabs() {
        pager = findViewById(R.id.ViewPager)
        tab = findViewById(R.id.tabs)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(ProfilFragment())
        adapter.addFragment(JadwalBaruFragment())
        adapter.addFragment(HistoryFragment())
        adapter.addFragment(CareFragment())
        pager.adapter = adapter
        tab.setupWithViewPager(pager)

        tab.getTabAt(0)!!.setIcon(R.drawable.home_logo)
        tab.getTabAt(1)!!.setIcon(R.drawable.profil)
        tab.getTabAt(2)!!.setIcon(R.drawable.add_alarm)
        tab.getTabAt(3)!!.setIcon(R.drawable.history)
        tab.getTabAt(4)!!.setIcon(R.drawable.self_care)


    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarm Berbunyi!"
            val descText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.add_alarm)
            .setContentTitle("Alarm Berbunyi")
            .setContentText("Ada jadwal yang harus kamu kerjakan!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@Home,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationID, builder.build())
        }

        var mp = MediaPlayer.create(applicationContext, R.raw.alarmringing)
        mp.start()
    }
}