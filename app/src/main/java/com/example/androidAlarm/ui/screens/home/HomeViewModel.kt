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
import androidx.lifecycle.viewModelScope
import com.example.androidAlarm.data.entity.AlarmEntity
import com.example.androidAlarm.domain.usecase.AddAlarmUseCase
import com.example.androidAlarm.domain.usecase.GetAlarmUseCase
import com.example.androidAlarm.model.Alarm
import com.example.androidAlarm.util.AlarmBroadcastReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject
import kotlin.streams.toList

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAlarmUseCase: GetAlarmUseCase,
    private val addAlarmUseCase: AddAlarmUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        getAlarm()
    }

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

    fun updateAlarmEnableFlag() {
        _uiState.update {
            it.copy(
                alarmIsEnable = !_uiState.value.alarmIsEnable
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addAlarmList(alarmTime: LocalTime) {
        addAlarm(
            Alarm(
                alarmClock = alarmTime.format(DateTimeFormatter.ofPattern("hh:mm")),
                isEnable = false
            )
        )
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

//    @SuppressLint("ScheduleExactAlarm")
//    private fun startAlarm(context: Context, selectTime: LocalTime) {
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.add(Calendar.SECOND, convertLocalTimeToSecond(selectTime))
//        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
//            context,
//            1,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        ContextCompat.getSystemService(
//            context,
//            AlarmManager::class.java
//        )?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//        ContextCompat.startForegroundService(context, intent)
//    }
//
//    private fun cancelAlarm(context: Context) {
//        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
//            context,
//            1,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        ContextCompat.getSystemService(
//            context,
//            AlarmManager::class.java
//        )?.cancel(pendingIntent)
//    }

    private fun getAlarm() = viewModelScope.launch {
        val alarmListEntity: List<AlarmEntity> = getAlarmUseCase.invoke()
        val alarmList: List<Alarm> = alarmListEntity.stream().map {
            Alarm(it.id, it.alarmTime, it.isEnable)
        }.toList()

        _uiState.update {
            it.copy(
                alarmList = alarmList
            )
        }
    }

    private fun addAlarm(alarm: Alarm) = viewModelScope.launch {
        val registerId: Long = addAlarmUseCase.invoke(alarm)
        val copyList: MutableList<Alarm> = _uiState.value.alarmList.toMutableList()
        copyList.add(Alarm(registerId, alarm.alarmClock, alarm.isEnable))
        _uiState.update {
            it.copy(
                alarmList = copyList
            )
        }
    }
}
