package com.example.androidAlarm

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                }
            )
        }
    }
}
