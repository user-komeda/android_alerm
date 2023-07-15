package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.remote.dataSource.NationalHolidayDataSource
import javax.inject.Inject

class NationalHolidayRepositoryImpl @Inject constructor(
    val nationalHolidayDataSource: NationalHolidayDataSource
) : NationalHolidayRepository {
    override suspend fun getNationalHoliday(): Map<String, String> {
        return nationalHolidayDataSource.getNationalHoliday().body()!!
    }
}
