package com.example.androidAlarm.ui.screens.detail

data class DetailState(
    val alarmTime: String,
    val isRepeat: Boolean,
    val ignoreTargetDays: List<String>,
    val alarmName: String,
    val groupConfig: String,
    val alarmSound: String,
    val soundVolume: Int,
    val isFadeIn: Boolean,
    val mannerModeVolume: String,
    val isVibration: Boolean,
    val vibrationStrength: String,
    val alarmSoundingTime: String,
    val isScreenLightWhenSleep: Boolean,
    val stopButtonBehavior: String,
    val snoozeButtonBehavior: String,
    val canSnooze: Boolean,
    val snoozeInterVal: String,
    val snoozeMaxCount: Int,
    val changeSnoozeSound: String,
)
