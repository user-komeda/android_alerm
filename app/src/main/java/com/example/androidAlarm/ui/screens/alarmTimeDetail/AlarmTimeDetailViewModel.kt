@file:Suppress("MagicNumber", "TooManyFunctions")

package com.example.androidAlarm.ui.screens.alarmTimeDetail

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.androidAlarm.model.ServiceState
import com.example.androidAlarm.util.AlarmBroadcastReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AlarmTimeDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmTimeDetailState())
    val uiState = _uiState.asStateFlow()

    fun updateTime() {
        _uiState.update {
            it.copy(
                nowTime = _uiState.value.nowTime.plusMinutes(1)
            )
        }
    }

    fun updateDate() {
        if (_uiState.value.nextDayDateTime.isAfter(_uiState.value.nowTime)) {
            return
        }
        _uiState.update {
            it.copy(
                nowDate = _uiState.value.nowDate.plusDays(1),
                nextDayDateTime = _uiState.value.nextDayDateTime.plusDays(1),
                dayOfWeek = _uiState.value.nowDate.plusDays(1).dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.JAPAN
                )
            )
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun snoozeAlarm(context: Context, selectTime: LocalTime) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, convertLocalTimeToSecond(selectTime))
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("serviceState", ServiceState.SNOOZE.name)
        context.sendBroadcast(intent)
        intent.putExtra("serviceState", ServiceState.START.name)
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

    fun stopAlarm(context: Context) {
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("serviceState", ServiceState.STOP.name)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        context.sendBroadcast(intent)
        ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )?.cancel(pendingIntent)
    }

    private fun convertLocalTimeToSecond(localTime: LocalTime): Int {
        return localTime.hour * 3600 * localTime.minute * 60 + localTime.second
    }
}
