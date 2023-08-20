package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity
import com.example.androidAlarm.data.room.dao.DesignatedDaysKeyDao

class DesignatedDaysKeyRepositoryImpl(private val designatedDaysKeyDao: DesignatedDaysKeyDao) :
    DesignatedDaysKeyRepository {

    override suspend fun updateDesignatedDateLabel(designatedDaysKeyEntity: DesignatedDaysKeyEntity) {
        return designatedDaysKeyDao.update(designatedDaysKeyEntity)
    }
}
