@file:Suppress("TooManyFunctions")

package com.example.androidAlarm.ui.screens.designatedDate

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.domain.usecase.AddDesignatedDateUseCase
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
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class DesignatedDateViewModel @Inject constructor(
    private val getNationalHolidayUseCase: GetNationalHolidayUseCase,
    private val getDesignatedDaysUseCase: GetDesignatedDaysUseCase,
    private val addDesignatedDateUseCase: AddDesignatedDateUseCase
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

    fun updateShowDateTimePicker2(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowDataTimePicker2 = flag
            )
        }
    }

    fun updateShowDateTimePicker3(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowDataTimePicker3 = flag
            )
        }
    }

    fun updateShowComplete(flag: Boolean) {
        _uiState.update {
            it.copy(
                isComplete = flag
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
        val copyList =
            copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]]!!.toMutableList()

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
            copyList.distinctBy {
                it.date
            }
            copyList.sortBy {
                it.date
            }
        }

        updateDesignatedDate(designatedDate = designatedDate)
        updateDesignatedDateName(designatedDateName = designatedDateName)

        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] = copyList
        addDesignatedDate(copyMap)
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
        val copyList =
            copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]]!!.toMutableList()
        copyList.add(
            NationalHoliday(
                designatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                designatedDateName
            )
        )
        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] =
            copyList.distinctBy {
                it.date
            }
        addDesignatedDate(copyMap)
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun addAllDesignatedMap(designatedDate: LocalDateTime, designatedDateName: String) {
        if (!_uiState.value.isComplete) {
            updateDesignatedDate(designatedDate = designatedDate)
            updateDesignatedDateName(designatedDateName = designatedDateName)
            _uiState.update {
                it.copy(
                    isComplete = true,
                    isShowDataTimePicker3 = true

                )
            }
            return
        }

        val daysDiff: Long = ChronoUnit.DAYS.between(_uiState.value.designatedDate, designatedDate)
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList =
            copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]]!!.toMutableList()
        for (i in 0..daysDiff) {
            copyList.add(
                NationalHoliday(
                    _uiState.value.designatedDate.plusDays(i)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    designatedDateName
                )
            )
        }

        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] =
            copyList.distinctBy {
                it.date
            }
        addDesignatedDate(copyMap)
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap,
                isComplete = false
            )
        }
    }

    fun deleteDesignatedDate() {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList =
            copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]]!!.toMutableList()

        copyList.filter {
            it.holidayName == _uiState.value.designatedDateName
        }.forEach {
            copyList.remove(it)
        }

        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] = copyList
        addDesignatedDate(copyMap)
        updateShowDesignatedModal(false)
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun deleteAllDesignatedDate() {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] =
            ArrayList()
        addDesignatedDate(copyMap)
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

    fun updateShowDesignatedDateLabelModal(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowDesignatedDateLabelModal = flag
            )
        }
    }

    fun updateSelectDesignatedDateLabel(selectDesignatedDateLabel: String) {
        _uiState.update {
            it.copy(
                selectDesignatedDateLabel = selectDesignatedDateLabel,
                editTextDesignatedDateLabel = selectDesignatedDateLabel
            )
        }
    }

    fun updateShowEditDesignatedDateLabel(flag: Boolean) {
        _uiState.update {
            it.copy(
                isShowEditDesignatedDateLabelModal = flag,
                isOkEditDesignatedDateLabelModal = false
            )
        }
    }

    @SuppressLint("BinaryOperationInTimber")
    fun updateEditTextDesignatedDateLabel(text: String) {
        _uiState.update {
            it.copy(
                editTextDesignatedDateLabel = text
            )
        }
    }

    fun updateDesignatedDateLabel(designateDateLabelName: String) {
        val index =
            _uiState.value.designatedDateMapKeyList.indexOf(_uiState.value.selectDesignatedDateLabel)
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList = _uiState.value.designatedDateMapKeyList.toMutableList()
        val value = copyMap[_uiState.value.selectDesignatedDateLabel]
        copyList[index] = designateDateLabelName
        copyMap.remove(_uiState.value.selectDesignatedDateLabel)
        copyMap[designateDateLabelName] = value!!
        addDesignatedDate(copyMap)
        _uiState.update {
            it.copy(
                designatedDateMapKeyList = copyList,
                designatedDateMap = copyMap,
                isOkEditDesignatedDateLabelModal = false,
            )
        }
    }

    private fun getNationalHoliday() = viewModelScope.launch {
        val result: List<NationalHoliday> = getNationalHolidayUseCase.invoke(1)
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()

        val copyList =
            if (copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]].isNullOrEmpty()) {
                mutableListOf()
            } else {
                copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]]!!.toMutableList()
            }
        for (value in result) {
            copyList.add(value)
        }
        copyList.distinctBy {
            it.date
        }

        copyMap[_uiState.value.designatedDateMapKeyList[_uiState.value.selectTabIndex]] =
            copyList.distinctBy { it.date }
        addDesignatedDate(copyMap)
        Timber.d(copyMap.toString())
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    private fun getDesignatedDate() = viewModelScope.launch {
        val result: Map<String, List<DesignatedDaysEntity>> =
            getDesignatedDaysUseCase.invoke().groupBy { it.designatedDateGroup }
        val resultMap = mutableMapOf<String, List<NationalHoliday>>()
        result.keys.forEach {
            val nationalHolidayList = result[it]?.map {
                NationalHoliday(it.designatedDate, it.designatedDateName)
            }
            resultMap[it] = nationalHolidayList!!
        }
        _uiState.update {
            it.copy(
                designatedDateMap = resultMap
            )
        }
    }

    private fun addDesignatedDate(param: Map<String, List<NationalHoliday>>) =
        viewModelScope.launch {
            addDesignatedDateUseCase.invoke(param)
        }
}
