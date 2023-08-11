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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidAlarm.ui.components.AlarmDatePickerDialog
import com.example.androidAlarm.ui.components.AlarmDialog
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

        if (uiState.isShowAddDesignatedDateModal) {
            AddDesignatedDateModal(designatedDateViewModel = designatedDateViewModel)
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

@RequiresApi(Build.VERSION_CODES.O)
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
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { designatedDateViewModel.updateShowAddDesignatedDateModal(true) }
            ) {
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

@RequiresApi(Build.VERSION_CODES.O)
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
                designatedDateViewModel.updateDesignatedDate(
                    designatedDate = LocalDate
                        .parse(
                            designatedDate,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        )
                        .atStartOfDay()
                )
                designatedDateViewModel.updateDesignatedDateName(designatedDateName = designatedDateName)
            }
    ) {
        Text(text = designatedDate)
        Text(text = designatedDateName)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DesignatedDateModal(
    designatedDateViewModel: DesignatedDateViewModel,
    uiState: DesignatedDateState
) {
    AlarmDialog(
        title = uiState.designatedDateName,
        text1 = "変更",
        text2 = "削除",
        method1 = { designatedDateViewModel.updateShowDateTimePicker(true) },
        method2 = { designatedDateViewModel.deleteDesignatedDate() },
        onDismissRequest = { designatedDateViewModel.updateShowDesignatedModal(false) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType", "UnrememberedMutableState")
@Composable
private fun DatePickerDialogSample(
    designatedDateViewModel: DesignatedDateViewModel,
    uiState: DesignatedDateState
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    val instant = state.selectedDateMillis?.let { Instant.ofEpochMilli(it) }

    AlarmDatePickerDialog(
        onDismissRequest = { designatedDateViewModel.updateShowDateTimePicker(false) },
        onClickConfirmButton = {
            designatedDateViewModel.updateShowDateTimePicker(false)
            designatedDateViewModel.updateDesignatedObject(
                LocalDateTime.ofInstant(
                    instant,
                    ZoneId.systemDefault()
                ),
                uiState.designatedDateName
            )
        },
        onCClickDismissButton = { designatedDateViewModel.updateShowDateTimePicker(false) },
        textFieldValue = uiState.designatedDateName,
        onValueChange = { newValue -> designatedDateViewModel.updateDesignatedDateName(newValue) },
        state = state
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AddDesignatedDateModal(
    designatedDateViewModel: DesignatedDateViewModel
) {
    AlarmDialog(
        title = "指定日の追加",
        text1 = "1日追加",
        text2 = "開始・終了日追加",
        method1 = {
            designatedDateViewModel.updateShowDateTimePicker(true)
        },
        method2 = { designatedDateViewModel.updateShowDateTimePicker(true) },
        onDismissRequest = { designatedDateViewModel.updateShowAddDesignatedDateModal(false) }
    )
}
