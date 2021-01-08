package com.example.sohan.customcalender.ui

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.sohan.customcalender.Api
import com.example.sohan.customcalender.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object InteractiveCalendarApp {

    private lateinit var pref: SharedPreferences

    lateinit var apiService: Api

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
        InteractiveCalendarApp.context = context
        initRetrofitLib()
        initSharedPref()
    }

    private fun initSharedPref() {
        pref = context.getSharedPreferences("", Activity.MODE_PRIVATE)
    }

    private fun initRetrofitLib() {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()

        apiService = retrofit.create(Api::class.java)
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun saveStateId(id: String){
        val editor = pref.edit()
        editor.putString("State_Id", id)
        editor.apply()
    }

    fun getStateId() : String?{
       return pref.getString("State_Id", null)
    }
}