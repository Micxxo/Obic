package com.mico.obic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mico.obic.fragments.*
import com.mico.obic.fragments.adapters.ViewPagerAdapter

class Home : AppCompatActivity() {
    lateinit var pager: ViewPager
    lateinit var tab: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        setUpTabs()

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
        tab.getTabAt(1)!!.setIcon(R.drawable.add_alarm)
        tab.getTabAt(2)!!.setIcon(R.drawable.profil)
        tab.getTabAt(3)!!.setIcon(R.drawable.history)
        tab.getTabAt(4)!!.setIcon(R.drawable.self_care)
    }




}