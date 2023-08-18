package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class GetDesignatedDaysUseCase @Inject constructor(
    private val designatedDaysRepository: DesignatedDaysRepository
) : BaseNoParamUseCase<List<DesignatedDaysKeyWithDesignatedDays>> {

    override suspend fun invoke(): List<DesignatedDaysKeyWithDesignatedDays> {
        return designatedDaysRepository.getAllDesignatedDate()
    }
}
