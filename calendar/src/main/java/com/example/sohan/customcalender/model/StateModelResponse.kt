package com.example.sohan.customcalender.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateModelResponse(val id: String, val code: String, val name: String): Parcelable {
}