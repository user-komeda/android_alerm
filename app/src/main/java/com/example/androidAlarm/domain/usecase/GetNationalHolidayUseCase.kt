package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.repository.NationalHolidayRepository
import javax.inject.Inject

class GetNationalHolidayUseCase @Inject constructor(
    val nationalHolidayRepository: NationalHolidayRepository
) : BaseUseCase<Long, Map<String, String>> {
    override suspend fun invoke(param: Long): Map<String, String> {
        return nationalHolidayRepository.getNationalHoliday()
    }
}
