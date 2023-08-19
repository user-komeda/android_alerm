package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class DeleteDesignatedDateUseCase @Inject constructor(
    private val designatedDaysRepository: DesignatedDaysRepository
) : BaseUseCase<NationalHoliday, Unit> {
    override suspend fun invoke(param: NationalHoliday) {
        return designatedDaysRepository.deleteDesignatedDate(param)
    }
}
