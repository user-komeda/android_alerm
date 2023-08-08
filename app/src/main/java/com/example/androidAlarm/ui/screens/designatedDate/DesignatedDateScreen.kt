@file:Suppress("MagicNumber")

package com.example.androidAlarm.ui.screens.designatedDate

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DesignatedDateScreen(
    designatedDateViewModel: DesignatedDateViewModel,
    navigateToCalendar: () -> Unit
) {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomBar(navigateToCalendar, designatedDateViewModel) }
    ) {
        val uiState by designatedDateViewModel.uiState.collectAsState()
        TabLayout(
            selectTabIndex = uiState.selectTabIndex,
            designatedDateViewModel,
            uiState
        )

        if (uiState.isShowDesignatedDateModal) {
            DesignatedDateModal(designatedDateViewModel, uiState)
        }
        if (uiState.isShowDataTimePicker) {
            DatePickerDialogSample(
                designatedDateViewModel = designatedDateViewModel,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "指定日の設定")
        }
    )
}

@Composable
private fun BottomBar(
    navigateToCalendar: () -> Unit,
    designatedDateViewModel: DesignatedDateViewModel
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "追加")
            }
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { designatedDateViewModel.getData() }
            ) {
                Text(text = "祝日の取得")
            }
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { designatedDateViewModel.deleteAllDesignatedDate() }
            ) {
                Text(text = "すべて削除")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(modifier = Modifier.weight(1f), onClick = navigateToCalendar) {
                Text(text = "カレンダーから選択")
            }
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "指定日のラベル変更")
            }
        }
    }
}

@Composable
private fun DesignatedDateList(
    designatedDateViewModel: DesignatedDateViewModel,
    designatedDate: String,
    designatedDateName: String
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                designatedDateViewModel.updateShowDesignatedModal(true)
                designatedDateViewModel.updateSelectedDate(designatedDateName)
            }
    ) {
        Text(text = designatedDate)
        Text(text = designatedDateName)
    }
}

@Composable
private fun TabLayout(
    selectTabIndex: Int,
    designatedDateViewModel: DesignatedDateViewModel,
    uiState: DesignatedDateState
) {
    val keyList: List<String> = uiState.designatedDateMapKeyList
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        ScrollableTabRow(selectedTabIndex = selectTabIndex, edgePadding = 0.dp) {
            keyList.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = selectTabIndex == index,
                    onClick = { designatedDateViewModel.update(index) },
                    icon = { Icons.Default.Home }
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(uiState.designatedDateMap[uiState.designatedDateMapKeyList[selectTabIndex]]!!) {
                DesignatedDateList(designatedDateViewModel, it.date, it.holidayName)
                Divider()
            }
        }
    }
}

@Composable
private fun DesignatedDateModal(
    designatedDateViewModel: DesignatedDateViewModel,
    uiState: DesignatedDateState
) {
    val heightSize = LocalConfiguration.current.screenHeightDp / 4 / 3
    Dialog(onDismissRequest = { designatedDateViewModel.updateShowDesignatedModal(false) }) {
        Surface(
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp).dp)
                .height((LocalConfiguration.current.screenHeightDp / 4).dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = (LocalConfiguration.current.screenWidthDp / 3).dp)
                        .height(heightSize.dp)
                ) {
                    Text(
                        text = uiState.selectedDate,
                        Modifier.padding(top = (heightSize / 3).dp),
                    )
                }
                Divider()
                Row(
                    Modifier
                        .height(heightSize.dp)
                        .fillMaxWidth()
                        .clickable { designatedDateViewModel.updateShowDateTimePicker(true) }
                ) {
                    Text(text = "変更", textAlign = TextAlign.Left)
                }
                Divider()
                Row(
                    Modifier
                        .height(heightSize.dp)
                        .fillMaxWidth()
                        .clickable { designatedDateViewModel.deleteDesignatedDate() }
                ) {
                    Text(text = "削除", textAlign = TextAlign.Left)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType", "UnrememberedMutableState")
@Composable
fun DatePickerDialogSample(
    designatedDateViewModel: DesignatedDateViewModel,
    uiState: DesignatedDateState
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    val instant = state.selectedDateMillis?.let { Instant.ofEpochMilli(it) }
    Timber.d(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toString())
    DatePickerDialog(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = {
            designatedDateViewModel.updateShowDateTimePicker(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    designatedDateViewModel.updateShowDateTimePicker(false)
                    Timber.d(state.selectedDateMillis.toString())
                },
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    designatedDateViewModel.updateShowDateTimePicker(false)
                }
            ) {
                Text("Cancel")
            }
        },
        content = {
            DesignatedDateField(
                uiState = uiState,
                designatedDateViewModel = designatedDateViewModel
            )
            DatePicker(state = state, title = null)
        },
    )
}

@Composable
private fun DesignatedDateField(
    uiState: DesignatedDateState,
    designatedDateViewModel: DesignatedDateViewModel
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.designatedDateName,
            onValueChange = { newValue -> designatedDateViewModel.updateDesignatedDateName(newValue) }
        )
    }
}
