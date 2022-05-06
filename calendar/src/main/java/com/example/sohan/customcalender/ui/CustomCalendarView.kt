package com.example.sohan.customcalender.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.sohan.customcalender.R
import com.example.sohan.customcalender.util.showPopupWindow
import java.text.SimpleDateFormat
import java.util.*

class CustomCalendarView : FrameLayout {
    private lateinit var listOfYear: List<Int>
    private lateinit var callBack: (Calendar?) -> Unit
    private var events: HashSet<Date>? = null

    // internal components
    private var header: LinearLayout? = null
    private var btnPrev: ImageView? = null
    private var btnNext: ImageView? = null
    private var txtDate: TextView? = null
    private var grid: GridView? = null

    companion object {
        private const val DAYS_COUNT = 35
    }

    // current displayed month
    private val currentDate = Calendar.getInstance()

    constructor(context: Context?) : super(context!!)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initControl(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initControl(context)
    }

    /**
     * Load component XML layout
     */
    private fun initControl(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cust_calender_view, this)

        // layout is inflated, assign local variables to components
        header = findViewById<View>(R.id.cus_cal_calendar_header) as LinearLayout
        btnPrev = findViewById<View>(R.id.calendar_prev_button) as ImageView
        btnNext = findViewById<View>(R.id.calendar_next_button) as ImageView
        txtDate = findViewById<View>(R.id.cu_cal_calendar_date_display) as TextView
        grid = findViewById<View>(R.id.calendar_grid) as GridView
        val adapter = CalendarAdapter(getContext(), ArrayList())
        grid!!.adapter = adapter
        setListeners()
    }

    /**
     * Display dates in grid
     */
    fun showCalendar(events: HashSet<Date>? = null, callBack: (Calendar?) -> Unit) {
        this.events = events
        this.callBack = callBack
        if (!isInEditMode) {
            val cells = ArrayList<Date>()
            val calendar = currentDate.clone() as Calendar

            // determine the cell for current month's beginning
            calendar[Calendar.DAY_OF_MONTH] = 1
            val monthBeginningCell = calendar[Calendar.DAY_OF_WEEK] - 1

            // move calendar backwards to the beginning of the week
            calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

            // fill cells (35 days calendar as per our business logic)
            while (cells.size < DAYS_COUNT) {
                cells.add(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            // adding one more row in the current month for the  MONTH ends 30 or 31 which was showing in the next month and skipping if is 1st
            // eventually it will show in the next month
            while (monthBeginningCell >= 5 && calendar.get(Calendar.DAY_OF_MONTH) != 1) {
                cells.add(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            // if the date ends in sunday column then fill the entire row for the beatification till saturday column
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                cells.add(calendar.time)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            // update grid
            (grid!!.adapter as CalendarAdapter).updateData(
                cells,
                this.events,
                callBack,
                currentDate
            )

            // update title
            val sdf = SimpleDateFormat("MMMM yyyy", Locale.US)
            txtDate!!.text = sdf.format(currentDate.time)
        }
    }

    private fun setListeners() {
        // add one month and refresh UI
        btnNext!!.setOnClickListener {
            currentDate.add(Calendar.MONTH, 1)
            showCalendar(this.events, callBack)
            callBack(null)
        }

        // subtract one month and refresh UI
        btnPrev!!.setOnClickListener {
            currentDate.add(Calendar.MONTH, -1)
            showCalendar(this.events, callBack)
            callBack(null)
        }

        txtDate!!.setOnClickListener {
            showPopupWindow(it, context, listOfYear) { year ->
                currentDate.set(Calendar.YEAR, year)
                showCalendar(this.events, callBack)
            }
        }
    }


    fun setListOfYear(listOfYear: List<Int>) {
        this.listOfYear = listOfYear
    }
}