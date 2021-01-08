package com.example.sohan.customcalender.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class HolidayResponseModel(val res: HashMap<String, HashMap<String, List<HolidayMonthDetail>>>)
    : Parcelable{

    @Parcelize
    class HolidayMonthDetail(val date: String, val Reason_EN: String) : Parcelable
}