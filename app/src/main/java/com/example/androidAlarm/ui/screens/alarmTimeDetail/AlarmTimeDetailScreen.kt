@file:Suppress("LongMethod", "UnnecessaryParenthesesBeforeTrailingLambda", "MagicNumber")

package com.example.androidAlarm.ui.screens.alarmTimeDetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlarmTimeDetailScreen(
    alarmTimeDetailViewModel: AlarmTimeDetailViewModel,
    navigateToHomeScreen: () -> Unit
) {
    val uiState by alarmTimeDetailViewModel.uiState.collectAsState()
    Scaffold {
        AlarmTimeDetail(uiState, alarmTimeDetailViewModel, navigateToHomeScreen)
    }

    BackHandler(
        enabled = false,
        onBack = {
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AlarmTimeDetail(
    uiState: AlarmTimeDetailState,
    alarmTimeDetailViewModel: AlarmTimeDetailViewModel,
    navigateToHomeScreen: () -> Unit
) {
    val context: Context = LocalContext.current
    val nowTime = uiState.nowTime
    val nowDate = uiState.nowDate
    LaunchedEffect(uiState.dayOfWeek) {
        while (true) {
            alarmTimeDetailViewModel.updateTime()
            alarmTimeDetailViewModel.updateDate()
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
            Text(text = nowDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + uiState.dayOfWeek)
        }
        Row {
            Text(
                text = nowTime.format(DateTimeFormatter.ofPattern("hh:mm")),
                fontSize = 32.sp
            )
        }
        Row {
            Column {
                TextButton(onClick = {
                    alarmTimeDetailViewModel.snoozeAlarm(
                        context,
                        LocalTime.of(0, 0, 10)
                    )
                    navigateToHomeScreen()
                }) {
                    Text(text = "スヌーズ")
                }
            }
            Column {
                TextButton(onClick = {
                    alarmTimeDetailViewModel.stopAlarm(context = context)
                    navigateToHomeScreen()
                }) {
                    Text(text = "ストップ")
                }
            }
        }
    }
}
