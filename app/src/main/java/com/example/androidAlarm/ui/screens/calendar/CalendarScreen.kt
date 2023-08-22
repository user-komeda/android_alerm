package com.example.androidAlarm.ui.screens.calendar

import android.annotation.SuppressLint
import android.os.Build
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androidAlarm.ui.screens.designatedDate.DesignatedDateViewModel
import timber.log.Timber
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(
    designatedDateViewModel: DesignatedDateViewModel,
    parameter: String,
    navigateToDesignatedDate: () -> Unit
) {
    Timber.d(parameter)
    Scaffold {
        Calendar(parameter.split(","), designatedDateViewModel, navigateToDesignatedDate)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(
    parameter: List<String>,
    designatedDateViewModel: DesignatedDateViewModel,
    navigateToDesignatedDate: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.Top
    ) {
        AndroidView(
            { CalendarView(it) },
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            update = { views ->
                views.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                    val date = LocalDate.of(year, month + 1, dayOfMonth)
                    designatedDateViewModel.update(parameter[1].toLong())
                    designatedDateViewModel.addDesignatedMap(
                        date,
                        parameter[0],
                        parameter[1].toLong()
                    )
                    Toast.makeText(
                        context,
                        date.toString() + parameter[0] + "に追加しました",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        BackHandler(
            enabled = true,
            onBack = {
                navigateToDesignatedDate()
            },
        )
    }
}
