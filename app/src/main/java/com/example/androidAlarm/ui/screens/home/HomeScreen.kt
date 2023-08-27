@file:Suppress("UnusedParameter", "MagicNumber")

package com.example.androidAlarm.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidAlarm.model.Alarm
import com.example.androidAlarm.model.HomeModalItem
import com.example.androidAlarm.ui.components.AlarmTimePicker
import com.example.androidalerm.R
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navigateToDetail: () -> Unit,
    navigateToConfig: () -> Unit,
    navigateToDestinationDate: () -> Unit,
    navigateToAlarmTime: (Int) -> Unit

) {
    val uiState by homeViewModel.uiState.collectAsState()
    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0
    )

    Scaffold(topBar = {
        AppBar(
            homeViewModel,
            isShowDropDownMenu = uiState.isShowDropDownMenu,
            navigateToConfig,
            navigateToDestinationDate,
        )
    }, bottomBar = {
        BottomBar()
    }) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Divider()
                    HomeItem(homeViewModel)
                    Divider()
                }
                items(uiState.alarmList) { alarm ->
                    HomeListItem(
                        alarm = alarm,
                        onClickItem = navigateToDetail
                    )
                    Divider()
                }
            }
        }
        if (uiState.isShowModal) {
            HomeModalView(
                homeViewModel = homeViewModel,
                uiState = uiState,
                navigateToAlarmTime = navigateToAlarmTime
            )
        }
        AlarmTimePicker(
            onDismissRequest = { homeViewModel.updateShowTimePickerFlag(false) },
            confirmRequest = {
                homeViewModel.addAlarmList(
                    LocalTime.of(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                )
            },
            isShowTimePicker = uiState.isShowTimePicker,
            timePickerState = timePickerState
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun AppBar(
    homeViewModel: HomeViewModel,
    isShowDropDownMenu: Boolean = false,
    navigateToConfig: () -> Unit = {},
    navigateToDestinationDate: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "スマートアラーム")
        },
        actions = {
            IconButton(onClick = { homeViewModel.update() }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "MoreVert")
            }
            DropdownMenu(
                expanded = isShowDropDownMenu,
                onDismissRequest = { homeViewModel.update() }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "クイックタイマー") },
                    onClick = { homeViewModel.updateShowModalFlag() }
                )
                DropdownMenuItem(text = { Text(text = "設定") }, onClick = navigateToConfig)
                DropdownMenuItem(
                    text = { Text(text = "指定日の設定") },
                    onClick = navigateToDestinationDate
                )
                DropdownMenuItem(text = { Text(text = "アラーム一括切り替え") }, onClick = { /*TODO*/ })
                DropdownMenuItem(text = { Text(text = "アラームを削除") }, onClick = { /*TODO*/ })
            }
        },
        modifier = Modifier
            .background(Color.White)
    )
}

@Composable
private fun BottomBar() {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = { }) {
                Text(text = "画面を閉じる")
            }
            TextButton(modifier = Modifier.weight(1f), onClick = { }) {
                Text(text = "すべてのメニューを表示")
            }
            TextButton(modifier = Modifier.weight(1f), onClick = { }) {
                Text(text = "ヘルプ")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_clock),
                    contentDescription = "時計アイコン"
                )
            }
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = {
                }
            ) {
                Text(text = "13:55")
            }
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { }
            ) {
                Text(text = "menu")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeItem(
    homeViewModel: HomeViewModel
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { homeViewModel.updateShowTimePickerFlag(true) }
        ) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = " stringResource(id = R.string.shopping_cart_content_desc)",
                modifier = Modifier.size(50.dp)
            )
            Text(text = "アラームの追加", fontSize = 28.sp)
        }
    }
}

@Composable
private fun HomeListItem(
    alarm: Alarm,
    onClickItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClickItem)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.icons8_clock),
            contentDescription = "時計アイコン"
        )
        Text(text = alarm.alarmClock, Modifier.padding(start = 10.dp), fontSize = 28.sp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeModalView(
    homeViewModel: HomeViewModel,
    uiState: HomeState,
    navigateToAlarmTime: (Int) -> Unit
) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { homeViewModel.updateShowModalFlag() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(HomeModalItem.values()) {
                    Row(
                        Modifier
                            .selectable(
                                selected = it.alarmTime == uiState.selectedAlarmTime,
                                onClick = {
                                    homeViewModel.selectTime(it.alarmTime, context)
                                    navigateToAlarmTime(it.alarmTime)
                                }
                            )
                            .fillMaxWidth()
                            .size(24.dp)
                    ) {
                        Text(text = it.viewItem)
                    }
                    Divider()
                }
            }
        }
    }
}
