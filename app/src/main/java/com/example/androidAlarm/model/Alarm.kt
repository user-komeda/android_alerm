package com.example.androidAlarm.model

data class Alarm(
    val resourceImageId: Int,
    val alarmClock: String,
    val repeatWeek: String? = null,
    val isRepeat: Boolean = false
)
