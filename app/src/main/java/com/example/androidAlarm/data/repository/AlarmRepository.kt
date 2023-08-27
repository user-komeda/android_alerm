package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.AlarmEntity
import com.example.androidAlarm.model.Alarm

interface AlarmRepository {

    suspend fun getAllAlarm(): List<AlarmEntity>

    suspend fun addAlarm(alarm: Alarm): Long

    suspend fun deleteAlarm(alarm: Alarm): Unit
}
