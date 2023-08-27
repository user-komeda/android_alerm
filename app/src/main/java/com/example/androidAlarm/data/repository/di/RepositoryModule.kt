package com.example.androidAlarm.data.repository.di

import com.example.androidAlarm.data.remote.dataSource.NationalHolidayDataSource
import com.example.androidAlarm.data.repository.AlarmRepository
import com.example.androidAlarm.data.repository.AlarmRepositoryImpl
import com.example.androidAlarm.data.repository.DesignatedDaysKeyRepository
import com.example.androidAlarm.data.repository.DesignatedDaysKeyRepositoryImpl
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import com.example.androidAlarm.data.repository.DesignatedDaysRepositoryImpl
import com.example.androidAlarm.data.repository.NationalHolidayRepository
import com.example.androidAlarm.data.repository.NationalHolidayRepositoryImpl
import com.example.androidAlarm.data.room.dao.AlarmDao
import com.example.androidAlarm.data.room.dao.DesignatedDaysDao
import com.example.androidAlarm.data.room.dao.DesignatedDaysKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNationalHolidayRepository(
        nationalHolidayDataSource: NationalHolidayDataSource
    ): NationalHolidayRepository {
        return NationalHolidayRepositoryImpl(nationalHolidayDataSource)
    }

    @Provides
    @Singleton
    fun provideDesignatedDaysRepository(
        designatedDaysDao: DesignatedDaysDao
    ): DesignatedDaysRepository {
        return DesignatedDaysRepositoryImpl(designatedDaysDao)
    }

    @Provides
    @Singleton
    fun provideDesignatedDaysKeyRepository(
        designatedDaysKeyDao: DesignatedDaysKeyDao
    ): DesignatedDaysKeyRepository {
        return DesignatedDaysKeyRepositoryImpl(designatedDaysKeyDao)
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(
        alarmDao: AlarmDao
    ): AlarmRepository {
        return AlarmRepositoryImpl(alarmDao)
    }
}
