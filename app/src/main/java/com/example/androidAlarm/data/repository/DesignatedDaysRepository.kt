package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays
import com.example.androidAlarm.data.model.NationalHoliday

interface DesignatedDaysRepository {
    suspend fun getAllDesignatedDate(): List<DesignatedDaysKeyWithDesignatedDays>

    suspend fun addDesignateDate(param: Map<Long, List<NationalHoliday>>): Unit

    suspend fun updateDesignatedDate(nationalHoliday: NationalHoliday): Unit

    suspend fun deleteDesignatedDate(param: NationalHoliday): Unit

    suspend fun deleteAllDesignatedDate(param: Long): Unit
}
