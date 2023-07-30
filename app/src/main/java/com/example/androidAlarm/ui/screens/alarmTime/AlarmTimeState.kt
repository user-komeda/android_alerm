package com.example.androidAlarm.ui.screens.alarmTime

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
data class AlarmTimeState constructor(
    val alarmTime: LocalTime,
    val elapsedTime: LocalTime,
    val flag: Boolean = false
)
