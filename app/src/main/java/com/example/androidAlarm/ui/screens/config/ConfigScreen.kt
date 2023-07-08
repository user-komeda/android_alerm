package com.example.androidAlarm.ui.screens.config

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidAlarm.model.ConfigItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel
) {
    val uiState by configViewModel.uiState.collectAsState()
    val configItem = ConfigItem()
    Scaffold(
        topBar = {
            AppBar(
                onClickItem = { configViewModel.update() },
                isShowDropDownMenu = uiState.isShowDropDownMenu
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(configItem.itemList) {
                ConfigListItem(it, {})
            }
        }
    }
}

@Composable
fun AppBar(
    onClickItem: () -> Unit,
    isShowDropDownMenu: Boolean
) {
    TopAppBar(
        title = {
            Text(text = "設定")
        },
        actions = {
            IconButton(onClick = onClickItem) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "MoreVert")
            }
            DropdownMenu(expanded = isShowDropDownMenu, onDismissRequest = onClickItem) {
                DropdownMenuItem(text = { Text(text = "追加設定") }, onClick = { /*TODO*/ })
            }
        },
        modifier = Modifier.background(Color.White)
    )
}

@Composable
fun ConfigListItem(
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
