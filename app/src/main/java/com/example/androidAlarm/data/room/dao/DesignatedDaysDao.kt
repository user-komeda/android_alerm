package com.example.androidAlarm.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.androidAlarm.data.entity.DesignatedDaysEntity

@Dao
abstract class DesignatedDaysDao : BaseDao<DesignatedDaysEntity> {

    @Query("select * from designatedDate")
    abstract suspend fun selectAll(): List<DesignatedDaysEntity>
}
