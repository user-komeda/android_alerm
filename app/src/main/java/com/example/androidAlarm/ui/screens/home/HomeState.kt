package com.example.androidAlarm.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.androidAlarm.model.Alarm
import com.example.androidAlarm.model.DropDownItem
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
data class HomeState constructor(
    val isShowDropDownMenu: Boolean = false,
    val alarmList: List<Alarm> = ArrayList(),
    val dropItemList: List<DropDownItem> = ArrayList(),
    val isShowModal: Boolean = false,
    val selectedAlarmTime: Int = 0,
    val isShowTimePicker: Boolean = false,
    val alarmTime: LocalTime = LocalTime.now(),
    val alarmIsEnable: Boolean = false
)
