package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.AlarmEntity
import com.example.androidAlarm.data.room.dao.AlarmDao
import com.example.androidAlarm.model.Alarm

class AlarmRepositoryImpl(private val alarmDao: AlarmDao) : AlarmRepository {

    override suspend fun getAllAlarm(): List<AlarmEntity> {
        return alarmDao.selectAll()
    }

    override suspend fun addAlarm(alarm: Alarm): Long {
        return alarmDao.insert(AlarmEntity.build(alarm))
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        return alarmDao.delete(AlarmEntity.build(alarm))
    }
}
