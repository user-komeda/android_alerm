package com.example.androidAlarm.ui.screens.home

import com.example.androidAlarm.model.Alarm
import com.example.androidAlarm.model.DropDownItem

data class HomeState(
    val isShowDropDownMenu: Boolean = false,
    val alarmList: List<Alarm> = ArrayList(),
    val dropItemList: List<DropDownItem> = ArrayList(),
    val isShowModal: Boolean = false,
    val selectedAlarmTime: Int = 0
)
