package com.example.androidAlarm.ui.screens.config

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ConfigState())
    val uiState: StateFlow<ConfigState> = _uiState.asStateFlow()

    fun update() {
        _uiState.update {
            it.copy(
                isShowDropDownMenu = !_uiState.value.isShowDropDownMenu
            )
        }
    }
}
