package com.example.androidAlarm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO dateの型をLocalDateTimeにしたい
@JsonClass(generateAdapter = true)
data class NationalHoliday(
    @Json(name = "date") val date: String,
    @Json(name = "name") val holidayName: String
)
