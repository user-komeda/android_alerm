package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.room.dao.DesignatedDaysDao

class DesignatedDaysRepositoryImpl(private val designatedDaysDao: DesignatedDaysDao) :
    DesignatedDaysRepository {
    override suspend fun getAllDesignatedDate(): List<DesignatedDaysEntity> {
        return designatedDaysDao.selectAll()
    }
}
