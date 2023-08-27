package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.entity.AlarmEntity
import com.example.androidAlarm.data.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : BaseNoParamUseCase<List<AlarmEntity>> {
    override suspend fun invoke(): List<AlarmEntity> {
        return alarmRepository.getAllAlarm()
    }
}
