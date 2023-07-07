package com.example.androidAlarm.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidAlarm.model.DetailConfigItem
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel
) {
//    val uiState = detailViewModel.uiState.collectAsState()
    val detailConfigItem = DetailConfigItem()
    Timber.d(detailViewModel.toString())

    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { AppBar() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(detailConfigItem.itemList) {
                DetailListItem(item = it, onClickItem = {})
            }
        }
    }
}

@Composable
fun DetailListItem(
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

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "アラームの設定")
        }
    )
}

@Composable
fun BottomBar() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
            Text(text = "aaa")
        }
        TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
            Text(text = "bbb")
        }
        TextButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
            Text(text = "ccc")
        }
    }
}
