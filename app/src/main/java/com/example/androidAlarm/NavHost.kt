@file:Suppress("LongMethod", "TooManyFunctions")

package com.example.androidAlarm

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.androidAlarm.ui.screens.alarmTime.AlarmTimeScreen
import com.example.androidAlarm.ui.screens.alarmTime.AlarmTimeViewModel
import com.example.androidAlarm.ui.screens.alarmTimeDetail.AlarmTimeDetailScreen
import com.example.androidAlarm.ui.screens.alarmTimeDetail.AlarmTimeDetailViewModel
import com.example.androidAlarm.ui.screens.calendar.CalendarScreen
import com.example.androidAlarm.ui.screens.config.ConfigScreen
import com.example.androidAlarm.ui.screens.config.ConfigViewModel
import com.example.androidAlarm.ui.screens.configDetail.ConfigDetailScreen
import com.example.androidAlarm.ui.screens.configDetail.ConfigDetailViewModel
import com.example.androidAlarm.ui.screens.designatedDate.DesignatedDateScreen
import com.example.androidAlarm.ui.screens.designatedDate.DesignatedDateViewModel
import com.example.androidAlarm.ui.screens.detail.DetailScreen
import com.example.androidAlarm.ui.screens.detail.DetailViewModel
import com.example.androidAlarm.ui.screens.home.HomeScreen
import com.example.androidAlarm.ui.screens.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AlarmDestination.HOME.name) {
        composable(route = AlarmDestination.HOME.name) {
            val homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                homeViewModel = homeViewModel,
                navigateToDetail = {
                    navController.navigate(AlarmDestination.HOME_DETAIL.name)
                },
                navigateToConfig = {
                    navController.navigate(AlarmDestination.CONFIG.name)
                },
                navigateToDestinationDate = {
                    navController.navigate(AlarmDestination.DESIGNATED_DATE.name)
                },
                navigateToAlarmTime = { alarmTime ->
                    navController.navigate("${AlarmDestination.ALARM_TIME.name}/$alarmTime")
                }
            )
        }
        composable(
            route = AlarmDestination.HOME_DETAIL.name
        ) {
            val detailViewModel: DetailViewModel = hiltViewModel<DetailViewModel>()
            DetailScreen(detailViewModel = detailViewModel)
        }
        composable(route = AlarmDestination.CONFIG.name) {
            val configViewModel: ConfigViewModel = hiltViewModel<ConfigViewModel>()
            ConfigScreen(
                configViewModel = configViewModel,
                navigateToDetailConfig = { navController.navigate(AlarmDestination.CONFIG_DETAIL.name) }
            )
        }
        composable(route = AlarmDestination.CONFIG_DETAIL.name) {
            val configDetailViewModel: ConfigDetailViewModel =
                hiltViewModel<ConfigDetailViewModel>()
            ConfigDetailScreen(configDetailViewModel = configDetailViewModel)
        }
        composable(route = AlarmDestination.DESIGNATED_DATE.name) {
            val designatedDateViewModel: DesignatedDateViewModel =
                hiltViewModel<DesignatedDateViewModel>()
            DesignatedDateScreen(
                designatedDateViewModel = designatedDateViewModel,
                navigateToCalendar = { navController.navigate(AlarmDestination.CALENDAR.name) }
            )
        }
        composable(route = AlarmDestination.CALENDAR.name) {
            CalendarScreen()
        }
        composable(route = "${AlarmDestination.ALARM_TIME.name}/{alarmTime}") {
            val alarmTimeViewModel: AlarmTimeViewModel = hiltViewModel<AlarmTimeViewModel>()
            AlarmTimeScreen(
                alarmTimeViewModel = alarmTimeViewModel,
                navigateToHomeScreen = {
                    navController.navigate(AlarmDestination.HOME.name)
                },
            )
        }
        composable(
            route = AlarmDestination.ALARM_TIME_DETAIL.name,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "myapp://alarmTimeDetail"
                    action = Intent.ACTION_VIEW
                }
            ),
        ) {
            val alarmTimeDetailViewModel: AlarmTimeDetailViewModel =
                hiltViewModel<AlarmTimeDetailViewModel>()
            AlarmTimeDetailScreen(
                alarmTimeDetailViewModel = alarmTimeDetailViewModel,
                navigateToHomeScreen = {
                    navController.navigate(AlarmDestination.HOME.name)
                }
            )
        }
    }
}
