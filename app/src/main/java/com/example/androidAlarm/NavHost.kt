package com.example.androidAlarm

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidAlarm.ui.screens.config.ConfigScreen
import com.example.androidAlarm.ui.screens.config.ConfigViewModel
import com.example.androidAlarm.ui.screens.detail.DetailScreen
import com.example.androidAlarm.ui.screens.detail.DetailViewModel
import com.example.androidAlarm.ui.screens.home.HomeScreen
import com.example.androidAlarm.ui.screens.home.HomeViewModel

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
                }
            )
        }
        composable(route = AlarmDestination.HOME_DETAIL.name) {
            val detailViewModel: DetailViewModel = hiltViewModel<DetailViewModel>()
            DetailScreen(detailViewModel = detailViewModel)
        }
        composable(route = AlarmDestination.CONFIG.name) {
            val configViewModel: ConfigViewModel = hiltViewModel<ConfigViewModel>()
            ConfigScreen(configViewModel = configViewModel)
        }
    }
}
