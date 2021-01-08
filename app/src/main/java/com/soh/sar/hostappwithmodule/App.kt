package com.soh.sar.hostappwithmodule

import android.app.Application
import com.example.sohan.customcalender.ui.InteractiveCalendarApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        InteractiveCalendarApp.init(this)
    }
}