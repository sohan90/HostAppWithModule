package com.example.sohan.customcalender.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 class CalendarDetailResponse(val states:  List<StateModelResponse>, val calendar:List<Calendar>,
                              val holidays: HashMap<String, HashMap<String,
        List<HolidayResponseModel.HolidayMonthDetail>>>)
 : Parcelable {

}

@Parcelize
class Calendar(val year:Int, val holidays: HashMap<String, HashMap<String,
        List<HolidayResponseModel.HolidayMonthDetail>>>?):Parcelable