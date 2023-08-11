@file:Suppress("MagicNumber", "LongParameterList")

package com.example.androidAlarm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AlarmDialog(
    title: String,
    text1: String,
    text2: String,
    method1: () -> Unit,
    method2: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val heightSize = LocalConfiguration.current.screenHeightDp / 4 / 3
    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                        text = title,
                        Modifier.padding(top = (heightSize / 3).dp),
                    )
                }
                Divider()
                Row(
                    Modifier
                        .height(heightSize.dp)
                        .fillMaxWidth()
                        .clickable { method1() }
                ) {
                    Text(text = text1, textAlign = TextAlign.Left)
                }
                Divider()
                Row(
                    Modifier
                        .height(heightSize.dp)
                        .fillMaxWidth()
                        .clickable { method2() }
                ) {
                    Text(text = text2, textAlign = TextAlign.Left)
                }
            }
        }
    }
}
