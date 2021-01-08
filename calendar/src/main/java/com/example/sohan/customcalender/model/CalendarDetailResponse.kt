package com.example.sohan.customcalender.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 class CalendarDetailResponse(val states:  List<StateModelResponse>, val holidays: HashMap<String, HashMap<String,
        List<HolidayResponseModel.HolidayMonthDetail>>>)
 : Parcelable {

}