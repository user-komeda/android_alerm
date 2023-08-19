package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class DeleteAllDesignatedDateUseCase @Inject constructor(
    private val designatedDaysRepository: DesignatedDaysRepository
) : BaseUseCase<Long, Unit> {
    override suspend fun invoke(param: Long) {
        return designatedDaysRepository.deleteAllDesignatedDate(param)
    }
}
