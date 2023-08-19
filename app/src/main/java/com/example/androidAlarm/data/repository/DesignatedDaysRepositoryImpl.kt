package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays
import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.room.dao.DesignatedDaysDao

class DesignatedDaysRepositoryImpl(private val designatedDaysDao: DesignatedDaysDao) :
    DesignatedDaysRepository {
    override suspend fun getAllDesignatedDate(): List<DesignatedDaysKeyWithDesignatedDays> {
        return designatedDaysDao.selectDesignatedDaysKeyWithDesignatedDays()
    }

    override suspend fun addDesignateDate(param: Map<Long, List<NationalHoliday>>) {
        return designatedDaysDao.insertAll(DesignatedDaysEntity.build(param))
    }

    override suspend fun deleteDesignatedDate(param: NationalHoliday) {
        return designatedDaysDao.delete(NationalHoliday.convertToDesignatedDaysEntity(param))
    }

    override suspend fun deleteAllDesignatedDate(param: Long) {
        return designatedDaysDao.deleteAllDesignatedDays(param)
    }
}
