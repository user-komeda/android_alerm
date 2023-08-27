package com.example.androidAlarm.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.androidAlarm.data.entity.AlarmEntity

@Dao
abstract class AlarmDao : BaseDao<AlarmEntity> {

    @Query("select * from alarm")
    abstract suspend fun selectAll(): List<AlarmEntity>
}
