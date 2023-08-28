package com.example.androidAlarm.model

data class Alarm(
    val id: Long = 0,
    val alarmClock: String,
    val alarmRequestCode: Int = 0,
    val isEnable: Boolean = false
)
