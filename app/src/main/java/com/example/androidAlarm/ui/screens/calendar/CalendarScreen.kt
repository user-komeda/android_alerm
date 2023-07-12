package com.example.androidAlarm.ui.screens.calendar

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen() {
    Scaffold {
        Calendar()
    }
}

@Composable
fun Calendar() {
    Row(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.Top
    ) {
        AndroidView(
            { CalendarView(it) },
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            update = { views ->
                views.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                    Timber.d(calendarView.toString())
                    Timber.d(year.toString())
                    Timber.d(month.toString())
                    Timber.d(dayOfMonth.toString())
                }
            }
        )
    }
}
