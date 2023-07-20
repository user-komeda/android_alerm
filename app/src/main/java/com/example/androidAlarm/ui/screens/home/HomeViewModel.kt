package com.example.androidAlarm.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    fun update() {
        _uiState.update {
            it.copy(
                isShowDropDownMenu = !_uiState.value.isShowDropDownMenu
            )
        }
    }

    fun updateShowModalFlag() {
        _uiState.update {
            it.copy(
                isShowDialogMenu = !_uiState.value.isShowDialogMenu
            )
        }
    }
}
