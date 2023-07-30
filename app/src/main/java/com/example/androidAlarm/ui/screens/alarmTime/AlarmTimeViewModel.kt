@file:Suppress("MagicNumber")
package com.example.androidAlarm.ui.screens.alarmTime

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class AlarmTimeViewModel @Inject constructor(
    handle: SavedStateHandle,
) : ViewModel() {
    private val alarmTime = handle.get<String>("alarmTime")!!.toInt()
    private val _uiState =
        MutableStateFlow(
            AlarmTimeState(
                alarmTime = LocalTime.of(
                    alarmTime / 3600,
                    alarmTime / 60,
                    0
                ),
                elapsedTime = LocalTime.of(alarmTime / 3600, alarmTime / 60, 0)
            )
        )
    val uiState = _uiState.asStateFlow()

    fun updateAlarmTime() {
        val elapsedTime = _uiState.value.elapsedTime
        val hour = elapsedTime.hour
        val minute = elapsedTime.minute
        val second = elapsedTime.second
        val flag = hour == 0 && minute == 0 && second == 0
        _uiState.update {
            it.copy(
                elapsedTime = _uiState.value.elapsedTime.minusSeconds(1),
                flag = flag
            )
        }
    }
}
