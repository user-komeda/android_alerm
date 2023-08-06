package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.repository.DesignatedDaysRepository
import javax.inject.Inject

class GetDesignatedDaysUseCase @Inject constructor(
    val designatedDaysRepository: DesignatedDaysRepository
) : BaseUseCase<Long, Map<String, String>> {
    override suspend fun invoke(param: Long): Map<String, String> {
        return TODO()
    }
}
