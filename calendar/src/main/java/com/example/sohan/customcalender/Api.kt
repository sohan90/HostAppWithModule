package com.example.sohan.customcalender

import com.example.sohan.customcalender.model.CalendarDetailResponse
import com.example.sohan.customcalender.model.CalendarModelResponse
import com.example.sohan.customcalender.model.ImageResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("https://cancalendarsnowtint-rhd8b.ondigitalocean.app/calendar")
    suspend fun getCalendarList() : List<CalendarDetailResponse>

    @GET("https://canaracalendar.snowtint.com/ads")
    suspend fun getBannerAds(): List<ImageResponseModel>


}