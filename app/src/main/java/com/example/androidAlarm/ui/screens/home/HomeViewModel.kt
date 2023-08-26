package com.example.androidAlarm.ui.screens.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.androidAlarm.model.Alarm
import com.example.androidAlarm.util.AlarmBroadcastReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    fun update() {
        _uiState.update {
            it.copy(
                isShowDropDownMenu = !_uiState.value.isShowDropDownMenu
            )
        }
    }

    fun updateShowModalFlag() {
        Timber.d("ulafm")
        _uiState.update {
            it.copy(
                isShowModal = !_uiState.value.isShowModal
            )
        }
    }

    fun updateShowTimePickerFlag(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowTimePicker = flag
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAlarmList(alarmTime: LocalTime) {
        val alarmList = _uiState.value.alarmList.toMutableList()
        alarmList.add(
            Alarm(
                alarmClock = alarmTime.format(DateTimeFormatter.ofPattern("hh:mm")),
                isEnable = false
            )
        )
        _uiState.update {
            it.copy(
                alarmList = alarmList
            )
        }
    }

    fun selectTime(selectTime: Int, context: Context) {
        _uiState.update {
            it.copy(
                selectedAlarmTime = selectTime
            )
        }
        Timber.d(selectTime.toString())
        setAlarm(selectTime, context)
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(selectTime: Int, context: Context) {
        Timber.d("aaaaaaaaaaaaa")
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, selectTime)
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarm = ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )
        if (alarm != null) {
            Timber.d("CCCCCCCCCCCCCCCCCCCC")
            alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            ContextCompat.startForegroundService(context, intent)
        }
    }
}
