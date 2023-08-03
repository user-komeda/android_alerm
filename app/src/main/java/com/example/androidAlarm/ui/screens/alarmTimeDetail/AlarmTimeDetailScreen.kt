package com.example.androidAlarm.ui.screens.alarmTimeDetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlarmTimeDetailScreen(
    alarmTimeDetailViewModel: AlarmTimeDetailViewModel
) {
    val uiState by alarmTimeDetailViewModel.uiState.collectAsState()
    Scaffold {
        AlarmTimeDetail(uiState, alarmTimeDetailViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AlarmTimeDetail(
    uiState: AlarmTimeDetailState,
    alarmTimeDetailViewModel: AlarmTimeDetailViewModel
) {
    val context: Context = LocalContext.current
    alarmTimeDetailViewModel.updateTime()
    LaunchedEffect(uiState.nowDate) {
        while (true) {
            delay(1000 * 60)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // 横方向
        verticalArrangement = Arrangement.Center // 縦方向
    ) {
        Row {
            Text(text = "タイマー1", fontSize = 32.sp)
        }
        Row {
            Text(text = "2023/08/02 水")
        }
        Row {
            Text(text = "13:28", fontSize = 32.sp)
        }
        Row {
            Column {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "スヌーズ")
                }
            }
            Column {
                TextButton(onClick = { alarmTimeDetailViewModel.stopAlarm(context = context) }) {
                    Text(text = "ストップ")
                }
            }
        }
    }
}
