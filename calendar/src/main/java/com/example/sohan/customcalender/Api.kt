package com.example.sohan.customcalender

import com.example.sohan.customcalender.model.CalendarModelResponse
import com.example.sohan.customcalender.model.ImageResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("https://canaracalendar.snowtint.com/calendars")
    suspend fun getCalendarList(@Query("year") date: Int) : List<CalendarModelResponse>

    @GET("https://canaracalendar.snowtint.com/ads")
    suspend fun getBannerAds(): List<ImageResponseModel>


}