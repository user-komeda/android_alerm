package com.example.androidAlarm.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlarmAlertDialog(
    alertTitle: String,
    alertText: String,
    clickConfirmButton: () -> Unit,
    clickDismissButton: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { },
        title = {
            Text(text = alertTitle)
        },
        text = {
            Text(text = alertText)
        },
        confirmButton = {
            TextButton(
                onClick = { // confirmをタップしたとき
                    clickConfirmButton()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { // confirmをタップしたとき
                    clickDismissButton()
                }
            ) {
                Text("キャンセル")
            }
        }
    )
}
