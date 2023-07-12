package com.example.androidAlarm.ui.screens.designatedDate

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidAlarm.model.DesignatedDateGroup

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DesignatedDateScreen(
    designatedDateViewModel: DesignatedDateViewModel,
    navigateToCalendar: () -> Unit
) {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomBar(navigateToCalendar) }
    ) {
        val uiState = designatedDateViewModel.uiState.collectAsState()
        TabLayout(
            selectTabIndex = uiState.value.selectTabIndex,
            designatedDateViewModel,
            uiState.value.designatedDateMap
        )
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "指定日の設定")
        }
    )
}

@Composable
fun BottomBar(navigateToCalendar: () -> Unit) {
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
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "祝日の取得")
            }
            TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
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
fun DesignatedDateList(
    item: String
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = item)
    }
}

@Composable
fun TabLayout(
    selectTabIndex: Int,
    designatedDateViewModel: DesignatedDateViewModel,
    designatedDateMap: Map<DesignatedDateGroup, List<String>>
) {
    val keyList: List<DesignatedDateGroup> = DesignatedDateState().designatedDateMap.keys.toList()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        ScrollableTabRow(selectedTabIndex = selectTabIndex, edgePadding = 0.dp) {
            keyList.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title.value) },
                    selected = selectTabIndex == index,
                    onClick = { designatedDateViewModel.update(index) },
                    icon = { Icons.Default.Home }
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(designatedDateMap[keyList[selectTabIndex]]!!) {
                DesignatedDateList(it)
                Divider()
            }
        }
    }
}