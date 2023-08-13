package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class GetDesignatedDaysUseCase @Inject constructor(
    private val designatedDaysRepository: DesignatedDaysRepository
) : BaseNoParamUseCase<List<DesignatedDaysEntity>> {

    override suspend fun invoke(): List<DesignatedDaysEntity> {
        return designatedDaysRepository.getAllDesignatedDate()
    }
}
