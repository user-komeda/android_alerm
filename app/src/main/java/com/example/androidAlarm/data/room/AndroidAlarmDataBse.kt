@file:Suppress("UnusedPrivateProperty")

package com.example.androidAlarm.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidAlarm.data.entity.AlarmEntity
import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity
import com.example.androidAlarm.data.room.dao.AlarmDao
import com.example.androidAlarm.data.room.dao.DesignatedDaysDao
import com.example.androidAlarm.data.room.dao.DesignatedDaysKeyDao
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [DesignatedDaysEntity::class, DesignatedDaysKeyEntity::class, AlarmEntity::class],
    version = 8
)
abstract class AndroidAlarmDataBse : RoomDatabase() {
    abstract fun getDesignatedDaysDao(): DesignatedDaysDao
    abstract fun getDesignatedDaysKeyDao(): DesignatedDaysKeyDao

    abstract fun getAlarmDao(): AlarmDao

    class Callback @Inject constructor(
        private val database: Provider<AndroidAlarmDataBse>,
    ) : RoomDatabase.Callback()
}
