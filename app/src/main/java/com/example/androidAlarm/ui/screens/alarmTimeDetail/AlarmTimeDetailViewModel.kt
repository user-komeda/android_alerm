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
import com.example.androidAlarm.util.AlarmBroadcastReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class AlarmTimeDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmTimeDetailState())
    val uiState = _uiState.asStateFlow()

    fun updateTime() {
        Timber.d(
            LocalDateTime.of(
                LocalDate.now().year,
                LocalDate.now().month,
                LocalDate.now().dayOfMonth + 1,
                0,
                0,
                1,
                0
            ).toString()
        )
    }

    fun updateDate() {
        if (_uiState.value.nextDayDateTime.isAfter(_uiState.value.nowDate)) {
            return
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun snoozeAlarm(context: Context, selectTime: LocalTime) {
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

    fun stopAlarm(context: Context) {
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
