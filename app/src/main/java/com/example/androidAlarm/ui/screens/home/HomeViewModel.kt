@file:Suppress("MagicNumber", "TooManyFunctions")

package com.example.androidAlarm.ui.screens.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
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
    private val application: Application,
    private val getAlarmUseCase: GetAlarmUseCase,
    private val addAlarmUseCase: AddAlarmUseCase
) : AndroidViewModel(application) {

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

    fun updateAlarmEnableFlag(alarm: Alarm) {
        val copyList = _uiState.value.alarmList.toMutableList()
        val filteredAlarm = copyList.filter {
            it.id == alarm.id
        }[0]
        Timber.d(alarm.toString())
        Timber.d(filteredAlarm.toString())
        Timber.d(copyList.size.toString())
        val removeIndex = (filteredAlarm.alarmRequestCode - 1)
        copyList.removeAt(removeIndex)
        copyList.add(
            removeIndex,
            Alarm(
                filteredAlarm.id,
                filteredAlarm.alarmClock,
                filteredAlarm.alarmRequestCode,
                !filteredAlarm.isEnable
            )
        )
        if (filteredAlarm.isEnable) {
            cancelAlarm(context = application.applicationContext, alarm.alarmRequestCode)
        } else {
            setAlarm(
                LocalTime.parse(alarm.alarmClock),
                application.applicationContext,
                alarm.alarmRequestCode
            )
        }

        _uiState.update {
            it.copy(
                alarmList = copyList
            )
        }
    }

    private fun updateAlarmRequestCode() {
        _uiState.update {
            it.copy(
                requestCode = _uiState.value.requestCode + 1
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addAlarmList(alarmTime: LocalTime) {
        updateAlarmRequestCode()
        Timber.d(_uiState.value.requestCode.toString())
//        Timber.d(alarmTime.format(DateTimeFormatter.ofPattern("HH:mm")))
        addAlarm(
            Alarm(
                alarmClock = alarmTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                alarmRequestCode = _uiState.value.requestCode,
                isEnable = false
            )
        )
    }

    fun selectTime(selectTime: Int, context: Context) {
        updateAlarmRequestCode()
        _uiState.update {
            it.copy(
                selectedAlarmTime = selectTime
            )
        }
        setAlarm(
            LocalTime.now().plusSeconds(selectTime.toLong()),
            context,
            _uiState.value.requestCode
        )
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(selectTime: LocalTime, context: Context, requestCode: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val nawSecond = convertLocalTimeToSecond(LocalTime.now())
        val selectTimeSecond = convertLocalTimeToSecond(selectTime)
        if (selectTimeSecond < nawSecond) {
            selectTimeSecond + 24
        }
        calendar.add(Calendar.SECOND, selectTimeSecond - nawSecond)
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarm = ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )
        if (alarm != null) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            ContextCompat.startForegroundService(context, intent)
        }
    }

    private fun cancelAlarm(context: Context, requestCode: Int) {
        val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )?.cancel(pendingIntent)
    }

    private fun getAlarm() = viewModelScope.launch {
        val alarmListEntity: List<AlarmEntity> = getAlarmUseCase.invoke()
        val alarmList: List<Alarm> = alarmListEntity.stream().map {
            Alarm(it.id, it.alarmTime, it.alarmRequestCode, it.isEnable)
        }.toList()

        _uiState.update {
            it.copy(
                alarmList = alarmList,
            )
        }
        if (alarmList.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    requestCode = alarmList.last().alarmRequestCode
                )
            }
        }
    }

    private fun addAlarm(alarm: Alarm) = viewModelScope.launch {
        val registerId: Long = addAlarmUseCase.invoke(alarm)
        val copyList: MutableList<Alarm> = _uiState.value.alarmList.toMutableList()
        copyList.add(
            Alarm(
                registerId,
                alarm.alarmClock,
                alarmRequestCode = alarm.alarmRequestCode,
                alarm.isEnable
            )
        )
        _uiState.update {
            it.copy(
                alarmList = copyList,
            )
        }
    }

    private fun convertLocalTimeToSecond(localTime: LocalTime): Int {
        return (localTime.hour * 3600) + (localTime.minute * 60) + localTime.second
    }
}
