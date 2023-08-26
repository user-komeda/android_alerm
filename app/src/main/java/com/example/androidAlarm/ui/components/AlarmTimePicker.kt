package com.example.androidAlarm.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("RememberReturnType")
@Composable
fun AlarmTimePicker(
    onDismissRequest: () -> Unit,
    confirmRequest: () -> Unit,
    isShowTimePicker: Boolean,
    timePickerState: TimePickerState
) {
    var selectedHour by remember {
        mutableStateOf(0)
    }

    var selectedMinute by remember {
        mutableStateOf(0) // or use  mutableStateOf(0)
    }

    if (isShowTimePicker) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            onDismissRequest = { onDismissRequest() }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // time picker
                TimePicker(state = timePickerState)

                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            onDismissRequest()
                            selectedHour = timePickerState.hour
                            selectedMinute = timePickerState.minute
                            confirmRequest()
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}
