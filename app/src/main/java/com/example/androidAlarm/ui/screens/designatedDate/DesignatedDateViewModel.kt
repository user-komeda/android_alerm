package com.example.androidAlarm.ui.screens.designatedDate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidAlarm.domain.usecase.GetDesignatedDaysUseCase
import com.example.androidAlarm.domain.usecase.GetNationalHolidayUseCase
import com.example.androidAlarm.model.DesignatedDateGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignatedDateViewModel @Inject constructor(
    private val getNationalHolidayUseCase: GetNationalHolidayUseCase,
    private val getDesignatedDaysUseCase: GetDesignatedDaysUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DesignatedDateState())
    val uiState = _uiState.asStateFlow()

    fun update(index: Int) {
        _uiState.update {
            it.copy(
                selectTabIndex = index
            )
        }
    }

    fun getData() {
        getDataJob()
        getDataJob2()
    }

    private fun getDataJob() = viewModelScope.launch {
        val result: Map<String, String> = getNationalHolidayUseCase.invoke(1)
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[DesignatedDateGroup.ONE_DESIGNATED_DATE] = result
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    private fun getDataJob2() = viewModelScope.launch {
        val result: Map<String, String> = getDesignatedDaysUseCase.invoke(1)
//        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
//        copyMap[DesignatedDateGroup.ONE_DESIGNATED_DATE] = result
//        _uiState.update {
//            it.copy(
//                designatedDateMap = copyMap
//            )
//        }
    }
}
