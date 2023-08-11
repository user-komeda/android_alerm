@file:Suppress("LongParameterList")

package com.example.androidAlarm.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmDatePickerDialog(
    onDismissRequest: (Boolean) -> Unit,
    onClickConfirmButton: () -> Unit,
    onCClickDismissButton: (Boolean) -> Unit,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    state: DatePickerState
) {
    DatePickerDialog(
        modifier = Modifier
            .fillMaxSize(),
        onDismissRequest = {
//            designatedDateViewModel.updateShowDateTimePicker(false)
            onDismissRequest(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
//                    designatedDateViewModel.updateShowDateTimePicker(false)
//                    designatedDateViewModel.updateDesignatedObject(
//                        LocalDateTime.ofInstant(
//                            instant,
//                            ZoneId.systemDefault()
//                        ),
//                        uiState.designatedDateName
//                    )
                    onClickConfirmButton()
                },
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
//                    designatedDateViewModel.updateShowDateTimePicker(false)
                    onCClickDismissButton(false)
                }
            ) {
                Text("Cancel")
            }
        },
        content = {
            DesignatedDateField(
                textFieldValue = textFieldValue,
                onValueChange = onValueChange
            )
            DatePicker(state = state, title = null)
        },
    )
}

@Composable
private fun DesignatedDateField(
    textFieldValue: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            label = { Text(text = "指定日名称") }
        )
    }
}
