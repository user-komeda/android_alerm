package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.model.NationalHoliday

interface NationalHolidayRepository {
    suspend fun getNationalHoliday(): List<NationalHoliday>
}
