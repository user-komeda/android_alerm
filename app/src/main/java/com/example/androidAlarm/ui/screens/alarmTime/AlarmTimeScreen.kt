@file:Suppress("MagicNumber")

package com.example.androidAlarm.ui.screens.alarmTime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidAlarm.ui.components.AlarmAlertDialog
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmTimeScreen(
    alarmTimeViewModel: AlarmTimeViewModel,
    navigateToHomeScreen: () -> Unit,
) {
    val uiState by alarmTimeViewModel.uiState.collectAsState()
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomBar(uiState, alarmTimeViewModel, navigateToHomeScreen) }
    ) {
        AlarmTIme(uiState, alarmTimeViewModel)
        if (uiState.isOpenDialog) {
            ConfirmDialog(
                alarmTimeViewModel = alarmTimeViewModel,
                navigateToHomeScreen = navigateToHomeScreen
            )
        }
    }

    BackHandler(
        enabled = true,
        onBack = {
            navigateToHomeScreen()
        },
    )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBar(
    uiState: AlarmTimeState,
    alarmTimeViewModel: AlarmTimeViewModel,
    navigateToHomeScreen: () -> Unit
) {
    if (uiState.isFinishAlarm) {
        return
    }
    val context = LocalContext.current
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = navigateToHomeScreen) {
                Text(text = "画面を閉じる")
            }
            SleepModeButton(
                uiState = uiState,
                alarmTimeViewModel = alarmTimeViewModel,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    alarmTimeViewModel.clickPauseButton(
                        context = context,
                        uiState.elapsedTime
                    )
                }
            ) {
                Text(text = "一時停止")
            }
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { alarmTimeViewModel.updateOpenDialogFlag(true) }
            ) {
                Text(text = "クリア")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmTIme(
    uiState: AlarmTimeState,
    alarmTimeViewModel: AlarmTimeViewModel
) {
    if (!uiState.isPausing && !uiState.isFinishAlarm) {
        LaunchedEffect(uiState.alarmTime) {
            while (true) {
                alarmTimeViewModel.updateElapsedTime()
                delay(1000)
            }
        }
    }
    AlarmPart(uiState = uiState)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SleepModeButton(
    uiState: AlarmTimeState,
    alarmTimeViewModel: AlarmTimeViewModel,
    modifier: Modifier
) {
    val context: Context = LocalContext.current
    if (uiState.isEnableSleepMode) {
        TextButton(
            modifier = modifier,
            onClick = { alarmTimeViewModel.disableSleepMode(context) }
        ) {
            Text(text = "画面点灯継続")
        }
    } else {
        TextButton(
            modifier = modifier,
            onClick = { alarmTimeViewModel.enableSleepMode(context) }
        ) {
            Text(text = "画面点灯継続を解除")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ConfirmDialog(
    alarmTimeViewModel: AlarmTimeViewModel,
    navigateToHomeScreen: () -> Unit
) {
    val context: Context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AlarmAlertDialog(
            alertTitle = "クイックタイマーの削除",
            alertText = "クリックタイマーを削除します",
            clickConfirmButton = {
                alarmTimeViewModel.clearAlarm(
                    context = context,
                    navigateToHomeScreen = { navigateToHomeScreen() }
                )
            },
            clickDismissButton = {
                alarmTimeViewModel.updateOpenDialogFlag(false)
            }
        )
    }
}
