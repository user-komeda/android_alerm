package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity
import com.example.androidAlarm.data.repository.DesignatedDaysKeyRepository
import javax.inject.Inject

class UpdateDesignatedDateLabelUseCase @Inject constructor(
    private val designatedDaysKeyRepository: DesignatedDaysKeyRepository
) : BaseUseCase<DesignatedDaysKeyEntity, Unit> {
    override suspend fun invoke(param: DesignatedDaysKeyEntity) {
        return designatedDaysKeyRepository.updateDesignatedDateLabel(param)
    }
}
