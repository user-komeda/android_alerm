package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.remote.dataSource.NationalHolidayDataSource
import javax.inject.Inject

class NationalHolidayRepositoryImpl @Inject constructor(
    private val nationalHolidayDataSource: NationalHolidayDataSource
) : NationalHolidayRepository {
    override suspend fun getNationalHoliday(): List<NationalHoliday> {
        return nationalHolidayDataSource.getNationalHoliday().body()!!
    }
}
