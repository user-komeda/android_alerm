package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class AddDesignatedDateUseCase @Inject constructor(
    private val designatedDaysRepository: DesignatedDaysRepository
) : BaseUseCase<Map<String, List<NationalHoliday>>, Unit> {

    override suspend fun invoke(param: Map<String, List<NationalHoliday>>) {
        return designatedDaysRepository.addDesignateDate(param)
    }
}
