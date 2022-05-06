package com.example.sohan.customcalender.ui

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.sohan.customcalender.R
import java.util.*


class CalendarAdapter(private val mContext: Context, private var mData: ArrayList<Date>) :
        ArrayAdapter<Date>(mContext, R.layout.cust_calendar_item_text, mData) {

    private lateinit var currentSelectedMonth: Calendar

    private var callBack: (Calendar) -> Unit = {}

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    private var eventDays: HashSet<Date>? = null

    private val selectedDate: MutableList<Date> = ArrayList()

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(i: Int): Date {
        return mData[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        // day in question
        var view = view
        val date = getItem(position)
        val calDate = Calendar.getInstance()
        calDate.time = date

        // inflate item if it does not exist yet
        if (view == null) {
            view = mInflater.inflate(R.layout.cust_calendar_item_text, viewGroup, false)
        }
        val dateTxt: TextView = view!!.findViewById(R.id.cus_cal_days) as TextView
        val underLine = view.findViewById<View>(R.id.cus_cal_under_line)
        val holidayBkg = view.findViewById<View>(R.id.holiday_background)
        val todayBkg = view.findViewById<View>(R.id.today_background)

        setBackgroundColor(dateTxt, underLine, holidayBkg, todayBkg, calDate)
        // set text
        view.tag = calDate
        dateTxt.text = calDate.get(Calendar.DAY_OF_MONTH).toString()

        view.setOnClickListener {
            val cal = it.tag as Calendar
            this.callBack(cal)
        }

        return view
    }

    private fun setBackgroundColor(dateTxt: TextView, underLine: View, holidayBkg: View, todayBkg: View,
                                   cal: Calendar) {

        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        val dayWeek = cal.get(Calendar.DAY_OF_WEEK)

        val today = Calendar.getInstance()

        todayBkg.visibility = View.INVISIBLE
        holidayBkg.visibility = View.INVISIBLE
        underLine.visibility = View.GONE
        dateTxt.setTextColor(ContextCompat.getColor(dateTxt.context, R.color.text_blue))
        dateTxt.setTypeface(null, Typeface.NORMAL)
        dateTxt.visibility = View.VISIBLE

        /*// clear styling
        if (month != today.get(Calendar.MONTH) || year != today.get(Calendar.YEAR)) {
            // if this day is outside current month, grey it out
            dateTxt.setTextColor(ContextCompat.getColor(dateTxt.context, R.color.light_grey))
        }*/

        // clear styling
        if (month != currentSelectedMonth.get(Calendar.MONTH) || year != currentSelectedMonth.get(Calendar.YEAR)) {
            // if this day is outside current month, grey it out
            dateTxt.setTextColor(ContextCompat.getColor(dateTxt.context, R.color.light_grey))
        }

        // today
        if (day == today.get(Calendar.DAY_OF_MONTH) && month == today.get(Calendar.MONTH)
                && year == today.get(Calendar.YEAR)) {

            todayBkg.visibility = View.VISIBLE
        }

        // for any event like holidays
        if (eventDays?.size!! > 0) {
            eventDays?.forEach { eventDate ->

                val evnCal = Calendar.getInstance()
                evnCal.time = eventDate

                val eventDay = evnCal.get(Calendar.DAY_OF_MONTH)
                val eventMonth = evnCal.get(Calendar.MONTH)
                val eventYear = evnCal.get(Calendar.YEAR)

                if (eventDay == day && eventMonth == month
                        && eventYear == year) {

                    holidayBkg.visibility = View.VISIBLE
                    underLine.visibility = View.VISIBLE
                }
            }
        }

        // weekend holiday for 2nd and 4th saturday
        if (dayWeek == Calendar.SATURDAY){
            val weekOfMonth = cal[Calendar.DAY_OF_WEEK_IN_MONTH]
            if (weekOfMonth == 2 || weekOfMonth == 4){
                // highlight with red color
                dateTxt.setTextColor(ContextCompat.getColor(dateTxt.context,
                    R.color.text_yellow))
                dateTxt.setTypeface(null, Typeface.BOLD)
            }
        }
        // weekend holiday for sunday
        if (dayWeek == Calendar.SUNDAY && month == currentSelectedMonth.get(Calendar.MONTH)) {
            // highlight with red color
            dateTxt.setTextColor(ContextCompat.getColor(dateTxt.context, R.color.text_yellow))
            dateTxt.setTypeface(null, Typeface.BOLD)
        }

    }

    fun updateData(cells: ArrayList<Date>, events: HashSet<Date>?, callBack: (Calendar) -> Unit,
                   currentSelectedMonth: Calendar) {
        mData = cells
        eventDays = events
        this.callBack = callBack
        this.currentSelectedMonth = currentSelectedMonth
        notifyDataSetChanged()
    }
}