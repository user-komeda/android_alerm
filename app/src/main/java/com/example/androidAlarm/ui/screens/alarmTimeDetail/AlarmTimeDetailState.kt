package com.example.androidAlarm.ui.screens.alarmTimeDetail

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
data class AlarmTimeDetailState constructor(
    val nowTime: LocalDateTime = LocalDateTime.now(),
    val nowDate: LocalDateTime = LocalDateTime.now(),
    val nextDayDateTime: LocalDateTime = LocalDateTime.of(
        LocalDateTime.now().year,
        LocalDateTime.now().month,
        LocalDateTime.now().dayOfMonth + 1,
        0,
        0,
        0,
        0
    ),
    val dayOfWeek: String = nowDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPAN)
)
