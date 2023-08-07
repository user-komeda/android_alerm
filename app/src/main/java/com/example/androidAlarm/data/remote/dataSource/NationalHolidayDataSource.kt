package com.example.androidAlarm.data.remote.dataSource

import com.example.androidAlarm.data.model.NationalHoliday
import retrofit2.Response

interface NationalHolidayDataSource {
    suspend fun getNationalHoliday(): Response<List<NationalHoliday>>
}
