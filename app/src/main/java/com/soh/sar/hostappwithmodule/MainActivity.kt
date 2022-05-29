package com.soh.sar.hostappwithmodule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.sohan.customcalender.ui.LandingActivity

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
    }
}