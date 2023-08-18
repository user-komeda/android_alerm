@file:Suppress("UnusedPrivateProperty")

package com.example.androidAlarm.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity
import com.example.androidAlarm.data.room.dao.DesignatedDaysDao
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [DesignatedDaysEntity::class, DesignatedDaysKeyEntity::class], version = 4)
abstract class AndroidAlarmDataBse : RoomDatabase() {
    abstract fun getArticleDao(): DesignatedDaysDao

    class Callback @Inject constructor(
        private val database: Provider<AndroidAlarmDataBse>,
    ) : RoomDatabase.Callback()
}
