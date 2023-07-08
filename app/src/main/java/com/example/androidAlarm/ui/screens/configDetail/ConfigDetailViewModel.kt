package com.example.androidAlarm.ui.screens.configDetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConfigDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ConfigDetailState())
    val uiState = _uiState.asStateFlow()
}
