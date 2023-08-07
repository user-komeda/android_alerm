package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.repository.NationalHolidayRepository
import javax.inject.Inject

class GetNationalHolidayUseCase @Inject constructor(
    private val nationalHolidayRepository: NationalHolidayRepository
) : BaseUseCase<Long, List<NationalHoliday>> {
    override suspend fun invoke(param: Long): List<NationalHoliday> {
        return nationalHolidayRepository.getNationalHoliday()
    }
}
