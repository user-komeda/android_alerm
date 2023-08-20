package com.example.androidAlarm.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity

@Dao
abstract class DesignatedDaysKeyDao : BaseDao<DesignatedDaysKeyEntity> {
    @Query("select * from designatedDaysKey")
    abstract suspend fun selectAll(): List<DesignatedDaysKeyEntity>
}
