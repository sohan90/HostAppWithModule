package com.example.sohan.customcalender.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sohan.customcalender.R
import com.example.sohan.customcalender.model.CalendarDetailResponse
import com.example.sohan.customcalender.model.CalendarModelResponse
import com.example.sohan.customcalender.model.ImageResponseModel
import com.example.sohan.customcalender.model.StateModelResponse
import com.example.sohan.customcalender.ui.InteractiveCalendarApp.apiService
import com.example.sohan.customcalender.ui.InteractiveCalendarApp.getJsonDataFromAsset
import com.example.sohan.customcalender.ui.InteractiveCalendarApp.getStateId
import com.example.sohan.customcalender.ui.InteractiveCalendarApp.init
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.cust_cal_activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class LandingActivity : AppCompatActivity() {

    private var calendarResponse: CalendarDetailResponse? = null

    private var holidayCalMap: HashMap<Calendar, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cust_cal_activity_main)
        init(this)
        showCalendar(HashSet())
        fetchStaticCalendar()
        setClickListener()
    }

    private fun showCalendar(hashSet: HashSet<Date>) {
        calenderView.showCalendar(hashSet) {
            showHolidayDetail(it)
        }

    }

    private fun setListOfYearToCalendarView() {
        val listOfYear = calendarResponse!!.calendar.map { it.year }
        calenderView.setListOfYear(listOfYear)
    }

    private fun setClickListener() {
        stateSelectionTxt.setOnClickListener {
            showStateListBottomSheetFragment()
        }
        arrow.setOnClickListener {
            finish()
        }
    }

    private fun showStateListBottomSheetFragment() {
        if (calendarResponse != null) {
            val list = calendarResponse!!.states as ArrayList<StateModelResponse>
            val fragment: BottomSheetDialogFragment =
                StateSelectBottomSheetFragment.newInstance(list)
            fragment.show(supportFragmentManager, "bottom_sheet_dialog")
        }
    }

    private fun fetchStaticCalendar() {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            /* val jsonFileString = getJsonDataFromAsset(applicationContext, "calendar.json")

                            val listPersonType = object : TypeToken<ArrayList<CalendarModelResponse>>() {}.type
                            val response: List<CalendarModelResponse> =
                                Gson().fromJson(jsonFileString, listPersonType)*/
            try {
                val response = apiService.getCalendarList()



                calendarResponse = response[0]
            } catch (e: Exception) {
                e.printStackTrace()
            }

            progressBar.visibility = View.GONE
            if (calendarResponse != null) {
                updateToolBarHeaderForState()
                val stateId = getStateId()

                if (TextUtils.isEmpty(stateId)) {
                    showStateListBottomSheetFragment()
                } else {
                    if (stateId != null) {
                        updateCalendarWithHolidays(stateId)
                    }
                }

            }
        }
    }

    private fun loadBannerImage(imageResponseModel: List<ImageResponseModel>) {
        val image = imageResponseModel[0]
        Glide.with(this).load("https://canaracalendar.snowtint.com" + image.image.url).into(adImg)

    }

    private fun updateToolBarHeaderForState() {
        val stateId = getStateId()
        if (!TextUtils.isEmpty(stateId)) {
            stateSelectionTxt.visibility = View.VISIBLE
            val state = calendarResponse?.states?.firstOrNull { it.id == stateId }
            stateSelectionTxt.text = state?.name
        } else {
            //stateSelectionTxt.visibility = View.GONE
        }
    }

    fun updateCalendarWithHolidays(stateId: String) {
        holidayCalMap.clear()
        holidayCardView.visibility = View.GONE
        updateToolBarHeaderForState()

        val holidayDateListForCalView: HashSet<Date> = java.util.HashSet()

        calendarResponse?.calendar?.forEach { calen ->
            val hashMap = calen.holidays
            if (hashMap != null) {
                for (key in hashMap.keys) {
                    if (key == stateId) {
                        val holidayDetailMap = hashMap[key]
                        holidayDetailMap?.forEach {

                            val cal = Calendar.getInstance()
                            cal.set(Calendar.MONTH, it.key.toInt() - 1)

                            cal.set(Calendar.YEAR, calen.year)

                            for (holidayMonthDetail in it.value) {
                                try {
                                    cal.set(Calendar.DAY_OF_MONTH, holidayMonthDetail.date.toInt())
                                    val cloneCalendar = cal.clone() as Calendar
                                    holidayDateListForCalView.add(cloneCalendar.time)
                                    holidayCalMap[cloneCalendar] = holidayMonthDetail.Reason_EN
                                } catch (e:Exception){
                                    e.printStackTrace()
                                }
                            }
                        }
                        break
                    }
                }
            }
        }

        showCalendar(holidayDateListForCalView)
        setListOfYearToCalendarView()
    }

    private fun showHolidayDetail(it: Calendar?) {
        if (it != null) {
            val selectedDay = it.get(Calendar.DAY_OF_MONTH)
            val selectedMonth = it.get(Calendar.MONTH)
            val selectedYear = it.get(Calendar.YEAR)

            for (mutableEntry in holidayCalMap) {
                val cal = mutableEntry.key
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val month = cal.get(Calendar.MONTH)
                val year = cal.get(Calendar.YEAR)

                if (selectedDay == day && selectedMonth == month && selectedYear == year) {
                    holidayCardView.visibility = View.VISIBLE
                    holidayDateTxt.text = selectedDay.toString()
                    holidayReasonTxt.text = mutableEntry.value
                    break
                }
            }
        } else {
            holidayCardView.visibility = View.GONE
        }
    }
}