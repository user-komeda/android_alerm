package com.example.androidAlarm.data.repository

interface NationalHolidayRepository {
    suspend fun getNationalHoliday(): Map<String, String>
}
