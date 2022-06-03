package com.soh.sar.hostappwithmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sohan.customcalender.ui.BlankActivity
import com.example.sohan.customcalender.ui.LandingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
    }
}