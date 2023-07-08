package com.example.androidAlarm.ui.screens.configDetail

data class ConfigDetailState(
    val alarmDelay: Boolean = false,
    val reeducationTextOnAlarmScreen: Boolean = false,
    val showStatusBarOnAlarmScreen: Boolean = false,
    val alarmNameLeftDisplay: Boolean = false,
    val displaySoundInfoOnAlarmScreen: Boolean = false,
    val displaySnoozeCountOnAlarmSound: Boolean = false,
    val automaticStopOverlapSound: Boolean = false,
    val stopSoundWhenAlarmActive: Boolean = false,
    val displayAlarmScreenOnLockScreen: Boolean = false,
)
