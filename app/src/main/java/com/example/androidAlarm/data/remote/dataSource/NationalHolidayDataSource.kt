package com.example.androidAlarm.data.remote.dataSource

import retrofit2.Response

interface NationalHolidayDataSource {
    suspend fun getNationalHoliday(): Response<Map<String, String>>
}
