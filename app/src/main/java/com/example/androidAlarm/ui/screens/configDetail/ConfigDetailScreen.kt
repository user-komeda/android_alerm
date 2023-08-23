package com.example.androidAlarm.ui.screens.configDetail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidAlarm.model.ConfigDetailItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfigDetailScreen(
    configDetailViewModel: ConfigDetailViewModel,
    navigateToConfig: () -> Unit
) {
    val uiState by configDetailViewModel.uiState.collectAsState()
    val configDetailItem = ConfigDetailItem()
    Scaffold(
        topBar = { AppBar() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(configDetailItem.itemList) {
                ConfigDetailListItem(item = it, {})
                Divider()
            }
        }
    }

    BackHandler(
        enabled = true,
        onBack = {
            navigateToConfig()
        },
    )
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "追加設定")
        }
    )
}

@Composable
fun ConfigDetailListItem(
    item: String,
    onClickItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClickItem)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = item)
    }
}
