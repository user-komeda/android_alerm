@file:Suppress("MagicNumber", "TooManyFunctions")

package com.example.androidAlarm.ui.screens.alarmTime

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androidAlarm.util.AlarmBroadcastReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class AlarmTimeViewModel @Inject
constructor(
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

    fun updateElapsedTime() {
        val elapsedTime = _uiState.value.elapsedTime
        val hour = elapsedTime.hour
        val minute = elapsedTime.minute
        val second = elapsedTime.second
        val flag = hour == 0 && minute == 0 && second == 0
        _uiState.update {
            it.copy(
                elapsedTime = _uiState.value.elapsedTime.minusSeconds(1),
                isFinishAlarm = flag
            )
        }
    }

    private fun updateAlarmTime(selectTime: LocalTime) {
        _uiState.update {
            it.copy(
                elapsedTime = selectTime,
                alarmTime = selectTime
            )
        }
    }

    fun disableSleepMode(context: Context) {
        val activity: Activity = context as Activity
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        _uiState.update {
            it.copy(
                isEnableSleepMode = false
            )
        }
    }

    fun enableSleepMode(context: Context) {
        val activity: Activity = context as Activity
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        _uiState.update {
            it.copy(
                isEnableSleepMode = true
            )
        }
    }

    private fun updatePauseFlag(flag: Boolean) {
        _uiState.update {
            it.copy(
                isPausing = flag
            )
        }
    }

    fun clearAlarm(context: Context, navigateToHomeScreen: () -> Unit) {
        updateOpenDialogFlag(false)
        cancelAlarm(context = context)
        navigateToHomeScreen()
    }

    fun updateOpenDialogFlag(flag: Boolean) {
        _uiState.update {
            it.copy(
                isOpenDialog = flag
            )
        }
    }

    fun clickPauseButton(context: Context, selectTime: LocalTime) {
        if (_uiState.value.isPausing) {
            updatePauseFlag(false)
            updateAlarmTime(selectTime = selectTime)
            startAlarm(context = context, selectTime = selectTime)
        } else {
            updatePauseFlag(true)
            cancelAlarm(context = context)
        }
    }

    private fun startAlarm(context: Context, selectTime: LocalTime) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, convertLocalTimeToSecond(selectTime))
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        ContextCompat.startForegroundService(context, intent)
    }

    private fun cancelAlarm(context: Context) {
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )?.cancel(pendingIntent)
    }

    private fun convertLocalTimeToSecond(localTime: LocalTime): Int {
        return localTime.hour * 3600 * localTime.minute * 60 + localTime.second
    }
}
