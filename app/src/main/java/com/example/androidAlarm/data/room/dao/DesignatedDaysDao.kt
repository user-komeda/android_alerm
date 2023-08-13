package com.example.androidAlarm.data.room.dao

import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query
import com.example.androidAlarm.data.entity.DesignatedDaysEntity

@Dao
abstract class DesignatedDaysDao : BaseDao<DesignatedDaysEntity> {

    @MapInfo(keyColumn = "designatedDateGroup")
    @Query("select id,designatedDate,designatedDateName,designatedDateGroup from designatedDate")
    abstract suspend fun selectAll(): List<DesignatedDaysEntity>
}
