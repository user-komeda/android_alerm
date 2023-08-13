package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.model.NationalHoliday

interface DesignatedDaysRepository {
    suspend fun getAllDesignatedDate(): List<DesignatedDaysEntity>

    suspend fun addDesignateDate(param: Map<String, List<NationalHoliday>>): Unit
}
