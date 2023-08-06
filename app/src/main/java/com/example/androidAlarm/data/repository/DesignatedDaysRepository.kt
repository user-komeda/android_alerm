package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysEntity

interface DesignatedDaysRepository {
    suspend fun getDesignatedDate(): DesignatedDaysEntity
}
