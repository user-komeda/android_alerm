package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.repository.AlarmRepository
import com.example.androidAlarm.model.Alarm
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : BaseUseCase<Alarm, Unit> {
    override suspend fun invoke(param: Alarm) {
        return alarmRepository.deleteAlarm(param)
    }
}
