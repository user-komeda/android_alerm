package com.example.androidAlarm.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays

@Dao
abstract class DesignatedDaysDao : BaseDao<DesignatedDaysEntity> {

    @Query("select * from designatedDate")
    abstract suspend fun selectAll(): List<DesignatedDaysEntity>

    @Transaction
    @Query("select * from DesignatedDaysKey where id IN (SELECT DISTINCT(designatedDaysKeyId) FROM designatedDate)")
    abstract suspend fun selectDesignatedDaysKeyWithDesignatedDays(): List<DesignatedDaysKeyWithDesignatedDays>
}
