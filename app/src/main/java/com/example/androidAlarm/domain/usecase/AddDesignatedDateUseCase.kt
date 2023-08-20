package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class AddDesignatedDateUseCase @Inject constructor(
    private val designatedDaysRepository: DesignatedDaysRepository
) : BaseUseCase<Map<Long, List<NationalHoliday>>, List<Long>> {

    override suspend fun invoke(param: Map<Long, List<NationalHoliday>>): List<Long> {
        return designatedDaysRepository.addDesignateDate(param)
    }
}
