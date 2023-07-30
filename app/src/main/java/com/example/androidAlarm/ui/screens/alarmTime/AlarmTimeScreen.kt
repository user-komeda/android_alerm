@file:Suppress("MagicNumber")

package com.example.androidAlarm.ui.screens.alarmTime

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmTimeScreen(
    alarmTimeViewModel: AlarmTimeViewModel,
) {
    val uiState by alarmTimeViewModel.uiState.collectAsState()
    Scaffold(topBar = { AppBar() }, bottomBar = { BottomBar(uiState) }) {
        AlarmTIme(uiState, alarmTimeViewModel)
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "クイックタイマー")
        },
        modifier = Modifier.background(Color.White)
    )
}

@Composable
fun BottomBar(
    uiState: AlarmTimeState
) {
    if (uiState.flag) {
        return
    }
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "画面を閉じる")
            }
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "画面点灯継続")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = { }) {
                Text(text = "一時停止")
            }
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "クリア")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmTIme(
    uiState: AlarmTimeState,
    alarmTimeViewModel: AlarmTimeViewModel,
) {
    val flag = uiState.flag
    if (!flag) {
        LaunchedEffect(uiState.alarmTime) {
            while (true) {
                alarmTimeViewModel.updateAlarmTime()
                delay(1000)
            }
        }
    }
    if (flag) {
        Text(text = "aaa")
    } else {
        AlarmPart(uiState = uiState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmPart(
    uiState: AlarmTimeState
) {
    val elapsedTime = uiState.elapsedTime
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // 横方向
        verticalArrangement = Arrangement.Center // 縦方向
    ) {
        Text(text = "タイマー1", textAlign = TextAlign.Center, fontSize = 32.sp)
        Text(
            text = String.format(
                (
                    "%02d%s%02d%s%02d".format(
                        elapsedTime.hour,
                        ":",
                        elapsedTime.minute,
                        ":",
                        elapsedTime.second
                    )
                    )
            ),
            fontSize = 32.sp
        )
    }
}
