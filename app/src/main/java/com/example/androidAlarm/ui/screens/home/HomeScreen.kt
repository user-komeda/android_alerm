@file:Suppress("UnusedParameter", "MagicNumber")

package com.example.androidAlarm.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidAlarm.model.Alarm
import com.example.androidAlarm.model.HomeModalItem
import com.example.androidalerm.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navigateToDetail: () -> Unit,
    navigateToConfig: () -> Unit,
    navigateToDestinationDate: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val context: Context = LocalContext.current

    Scaffold(topBar = {
        AppBar(
            { homeViewModel.update() },
            isShowDropDownMenu = uiState.isShowDropDownMenu,
            navigateToConfig,
            navigateToDestinationDate,
        )
    }) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(3) { alarm ->
                HomeListItem(
                    alarm = null,
                    onClickItem = navigateToDetail
                )
                Divider()
            }
        }
        if (!uiState.isShowDropDownMenu) {
            HomeModalView(onClickItem = { selectTime: Int ->
                homeViewModel.selectTime(
                    selectTime,
                    context
                )
            }, uiState = uiState)
        }
    }
}

@Preview
@Composable
private fun AppBar(
    onClickItem: () -> Unit = {},
    isShowDropDownMenu: Boolean = false,
    navigateToConfig: () -> Unit = {},
    navigateToDestinationDate: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "スマートアラーム")
        },
        actions = {
            IconButton(onClick = onClickItem) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "MoreVert")
            }
            DropdownMenu(expanded = isShowDropDownMenu, onDismissRequest = onClickItem) {
                DropdownMenuItem(text = { Text(text = "クイックタイマー") }, onClick = { /*TODO*/ })
                DropdownMenuItem(text = { Text(text = "設定") }, onClick = navigateToConfig)
                DropdownMenuItem(
                    text = { Text(text = "指定日の設定") },
                    onClick = navigateToDestinationDate
                )
                DropdownMenuItem(text = { Text(text = "アラーム一括切り替え") }, onClick = { /*TODO*/ })
                DropdownMenuItem(text = { Text(text = "アラームを削除") }, onClick = { /*TODO*/ })
            }
        },
        modifier = Modifier.background(Color.White)
    )
}

@Composable
private fun HomeListItem(
    alarm: Alarm?,
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
        Column(
            modifier = Modifier.padding(start = 24.dp)
        ) {
            Text(text = "abc")
            Text(text = "abc")
        }
    }
}

@Composable
private fun HomeModalView(
    onClickItem: (selectTime: Int) -> Unit,
    uiState: HomeState
) {
    Dialog(onDismissRequest = {}) {
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
                        Modifier.selectable(
                            selected = it.alarmTime == uiState.selectedAlarmTime,
                            onClick = { onClickItem(it.alarmTime) }
                        )
                    ) {
                        Text(text = it.viewItem)
                    }
                    Divider()
                }
            }
        }
    }

    Button({}) {
        Text(text = "OPEN")
    }
}
