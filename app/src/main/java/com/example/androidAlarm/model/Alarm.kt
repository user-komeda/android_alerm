package com.example.androidAlarm.model

data class Alarm(
    val id: Long,
    val alarmClock: String,
    val isEnable: Boolean = false
)
