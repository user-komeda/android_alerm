package com.example.androidAlarm.ui.screens.designatedDate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DesignatedDateViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(DesignatedDateState())
    val uiState = _uiState.asStateFlow()

    fun update(index: Int) {
        _uiState.update {
            it.copy(
                selectTabIndex = index
            )
        }
    }
}
