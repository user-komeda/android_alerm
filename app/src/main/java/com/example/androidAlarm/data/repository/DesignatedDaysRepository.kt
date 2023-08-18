package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays
import com.example.androidAlarm.data.model.NationalHoliday

interface DesignatedDaysRepository {
    suspend fun getAllDesignatedDate(): List<DesignatedDaysKeyWithDesignatedDays>

    suspend fun addDesignateDate(param: Map<String, List<NationalHoliday>>): Unit
}
