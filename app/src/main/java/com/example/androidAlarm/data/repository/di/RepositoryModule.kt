package com.example.androidAlarm.data.repository.di

import com.example.androidAlarm.data.remote.dataSource.NationalHolidayDataSource
import com.example.androidAlarm.data.repository.NationalHolidayRepository
import com.example.androidAlarm.data.repository.NationalHolidayRepositoryImpl
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
}
