@file:Suppress("TooManyFunctions")

package com.example.androidAlarm.ui.screens.designatedDate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.domain.usecase.GetDesignatedDaysUseCase
import com.example.androidAlarm.domain.usecase.GetNationalHolidayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.streams.toList

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class DesignatedDateViewModel @Inject constructor(
    private val getNationalHolidayUseCase: GetNationalHolidayUseCase,
    private val getDesignatedDaysUseCase: GetDesignatedDaysUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DesignatedDateState())
    val uiState = _uiState.asStateFlow()

    init {
        getDesignatedDate()
    }

    fun update(index: Int) {
        _uiState.update {
            it.copy(
                selectTabIndex = index
            )
        }
    }

    fun getData() {
        getNationalHoliday()
    }

    fun updateShowDateTimePicker(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowDataTimePicker = flag
            )
        }
    }

    fun updateDesignatedDateName(designatedDateName: String) {
        _uiState.update {
            it.copy(
                designatedDateName = designatedDateName
            )
        }
    }

    fun updateDesignatedDate(designatedDate: LocalDateTime) {
        _uiState.update {
            it.copy(
                designatedDate = designatedDate
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDesignatedObject(designatedDate: LocalDateTime, designatedDateName: String) {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList = copyMap[_uiState.value.designatedDateMapKeyList[0]]!!.toMutableList()

        copyList.filter {
            it.holidayName == _uiState.value.designatedDateName && LocalDate.parse(
                it.date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ).atStartOfDay() == _uiState.value.designatedDate
        }.forEach {
            copyList.remove(it)
            copyList.add(
                NationalHoliday(
                    designatedDate.toString(),
                    designatedDateName
                )
            )
            copyList.sortBy {
                it.date
            }
        }

        updateDesignatedDate(designatedDate = designatedDate)
        updateDesignatedDateName(designatedDateName = designatedDateName)

        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] = copyList
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun updateShowAddDesignatedDateModal(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowAddDesignatedDateModal = flag
            )
        }
    }

    fun addDesignatedMap(designatedDate: LocalDateTime, designatedDateName: String) {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList = copyMap[_uiState.value.designatedDateMapKeyList[0]]!!.toMutableList()
        copyList.add(NationalHoliday(designatedDate.toString(), designatedDateName))
        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] = copyList
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun addAllDesignatedMap() {
        TODO()
    }

    fun deleteDesignatedDate() {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList = copyMap[_uiState.value.designatedDateMapKeyList[0]]!!.toMutableList()

        copyList.filter {
            it.holidayName == _uiState.value.designatedDateName
        }.forEach {
            copyList.remove(it)
        }

        copyMap[_uiState.value.designatedDateMapKeyList[0]] = copyList

        updateShowDesignatedModal(false)
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun deleteAllDesignatedDate() {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[_uiState.value.designatedDateMapKeyList[0]] = ArrayList()
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun updateShowDesignatedModal(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowDesignatedDateModal = flag
            )
        }
    }

    private fun getNationalHoliday() = viewModelScope.launch {
        val result: List<NationalHoliday> = getNationalHolidayUseCase.invoke(1)
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] =
            result
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    private fun getDesignatedDate() = viewModelScope.launch {
        val result: List<DesignatedDaysEntity> = getDesignatedDaysUseCase.invoke(1)
        Timber.d(result.size.toString())
        val nationalHolidayList: List<NationalHoliday> = result.stream().map {
            it.convertToNationalHoliday()
        }.toList()
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[_uiState.value.designatedDateMapKeyList[0]] = nationalHolidayList
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }
}
