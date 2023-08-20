package com.example.androidAlarm.data.room.di

import android.app.Application
import androidx.room.Room
import com.example.androidAlarm.data.room.AndroidAlarmDataBse
import com.example.androidAlarm.data.room.dao.DesignatedDaysDao
import com.example.androidAlarm.data.room.dao.DesignatedDaysKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAndroidAlarmDataBase(
        application: Application,
        callback: AndroidAlarmDataBse.Callback
    ): AndroidAlarmDataBse {
        return Room.databaseBuilder(application, AndroidAlarmDataBse::class.java, "alarm_database")
            .fallbackToDestructiveMigration()
            .createFromAsset("database/init.db")
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideDesignatedDaysDao(db: AndroidAlarmDataBse): DesignatedDaysDao {
        return db.getDesignatedDaysDao()
    }

    @Provides
    fun provideDesignatedDaysKeyDao(db: AndroidAlarmDataBse): DesignatedDaysKeyDao {
        return db.getDesignatedDaysKeyDao()
    }
}
