package com.example.androidAlarm.domain.usecase

import com.example.androidAlarm.data.repository.AlarmRepository
import com.example.androidAlarm.model.Alarm
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) : BaseUseCase<Alarm, Long> {
    override suspend fun invoke(param: Alarm): Long {
        return alarmRepository.addAlarm(param)
    }
}
