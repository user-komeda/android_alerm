package com.example.androidAlarm.domain.di

import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays
import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import com.example.androidAlarm.data.repository.NationalHolidayRepository
import com.example.androidAlarm.domain.usecase.AddDesignatedDateUseCase
import com.example.androidAlarm.domain.usecase.BaseNoParamUseCase
import com.example.androidAlarm.domain.usecase.BaseUseCase
import com.example.androidAlarm.domain.usecase.DeleteAllDesignatedDateUseCase
import com.example.androidAlarm.domain.usecase.DeleteDesignatedDateUseCase
import com.example.androidAlarm.domain.usecase.GetDesignatedDaysUseCase
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
    ): BaseUseCase<Long, List<NationalHoliday>> {
        return GetNationalHolidayUseCase(nationalHolidayRepository)
    }

    @Provides
    @Singleton
    fun provideGetDesignatedDaysUseCase(
        designatedDaysRepository: DesignatedDaysRepository
    ): BaseNoParamUseCase<List<DesignatedDaysKeyWithDesignatedDays>> {
        return GetDesignatedDaysUseCase(designatedDaysRepository)
    }

    @Provides
    @Singleton
    fun provideAddDesignatedDateUseCase(
        designatedDaysRepository: DesignatedDaysRepository
    ): BaseUseCase<Map<Long, List<NationalHoliday>>, Unit> {
        return AddDesignatedDateUseCase(designatedDaysRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllDesignatedDateUseCase(
        designatedDaysRepository: DesignatedDaysRepository
    ): BaseUseCase<Long, Unit> {
        return DeleteAllDesignatedDateUseCase(designatedDaysRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteDesignatedDateUseCase(
        designatedDaysRepository: DesignatedDaysRepository
    ): BaseUseCase<NationalHoliday, Unit> {
        return DeleteDesignatedDateUseCase(designatedDaysRepository)
    }
}
