package com.example.androidAlarm.domain.di

import com.example.androidAlarm.data.repository.NationalHolidayRepository
import com.example.androidAlarm.domain.usecase.BaseUseCase
import com.example.androidAlarm.domain.usecase.GetNationalHolidayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    @Singleton
    fun provideGetNationalHolidayUseCase(
        nationalHolidayRepository: NationalHolidayRepository
    ): BaseUseCase<Long, Map<String, String>> {
        return GetNationalHolidayUseCase(nationalHolidayRepository)
    }
}
