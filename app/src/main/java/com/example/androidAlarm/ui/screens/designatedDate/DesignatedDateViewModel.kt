@file:Suppress("TooManyFunctions", "MagicNumber", "LongParameterList")

package com.example.androidAlarm.ui.screens.designatedDate

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity
import com.example.androidAlarm.data.entity.DesignatedDaysKeyWithDesignatedDays
import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.domain.usecase.AddDesignatedDateUseCase
import com.example.androidAlarm.domain.usecase.DeleteAllDesignatedDateUseCase
import com.example.androidAlarm.domain.usecase.DeleteDesignatedDateUseCase
import com.example.androidAlarm.domain.usecase.GetDesignatedDaysUseCase
import com.example.androidAlarm.domain.usecase.GetNationalHolidayUseCase
import com.example.androidAlarm.domain.usecase.UpdateDesignateDateUseCase
import com.example.androidAlarm.domain.usecase.UpdateDesignatedDateLabelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class DesignatedDateViewModel @Inject constructor(
    private val getNationalHolidayUseCase: GetNationalHolidayUseCase,
    private val getDesignatedDaysUseCase: GetDesignatedDaysUseCase,
    private val addDesignatedDateUseCase: AddDesignatedDateUseCase,
    private val updateDesignateDateUseCase: UpdateDesignateDateUseCase,
    private val updateDesignatedDateLabelUseCase: UpdateDesignatedDateLabelUseCase,
    private val deleteDesignatedDateUseCase: DeleteDesignatedDateUseCase,
    private val deleteAllDesignatedDateUseCase: DeleteAllDesignatedDateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DesignatedDateState())
    val uiState = _uiState.asStateFlow()

    init {
        getDesignatedDate()
        Timber.d(_uiState.value.selectTabIndex.toString())
    }

    fun update(index: Long) {
        Timber.d("calenderIndex")
        Timber.d(index.toString())
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

    fun updateDesignatedDate(designatedDate: LocalDate) {
        _uiState.update {
            it.copy(
                designatedDate = designatedDate
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDesignatedObject(designatedDate: LocalDate, designatedDateName: String) {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList = copyMap[_uiState.value.selectTabIndex]!!.toMutableList()
        copyList.filter {
            LocalDate.parse(
                it.date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ) == _uiState.value.designatedDate
        }.forEach {
            val updatedNationalHoliday = NationalHoliday(
                id = it.id,
                date = designatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                holidayName = designatedDateName,
                keyId = _uiState.value.selectTabIndex
            )
            copyList.remove(it)
            updateDesignatedDate(
                updatedNationalHoliday
            )
            copyList.add(updatedNationalHoliday)
        }

        updateDesignatedDate(designatedDate = designatedDate)
        updateDesignatedDateName(designatedDateName = designatedDateName)

        copyMap[_uiState.value.selectTabIndex] = copyList
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

    fun addDesignatedMap(designatedDate: LocalDate, designatedDateName: String, keyId: Long) {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        Timber.d("calender")
        Timber.d(_uiState.value.selectTabIndex.toString())
        Timber.d(_uiState.value.designatedDateMap.toString())
        val copyList: MutableList<NationalHoliday> =
            if (copyMap[_uiState.value.selectTabIndex].isNullOrEmpty()) {
                mutableListOf<NationalHoliday>()
            } else {
                copyMap[_uiState.value.selectTabIndex]!!.toMutableList()
            }

        copyList.add(
            NationalHoliday(
                date = designatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                holidayName = designatedDateName,
                keyId = keyId
            )
        )
        copyMap[keyId] = copyList.distinctBy {
            it.date
        }
        addDesignatedDate(copyMap)
    }

    fun addAllDesignatedMap(designatedDate: LocalDate, designatedDateName: String) {
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
        val copyList = if (copyMap[_uiState.value.selectTabIndex].isNullOrEmpty()) {
            mutableListOf<NationalHoliday>()
        } else {
            copyMap[_uiState.value.selectTabIndex]!!.toMutableList()
        }
        for (i in 0..daysDiff) {
            copyList.add(
                NationalHoliday(
                    date = _uiState.value.designatedDate.plusDays(i)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    holidayName = designatedDateName,
                    keyId = _uiState.value.selectTabIndex
                )
            )
        }

        copyMap[_uiState.value.selectTabIndex] = copyList.distinctBy {
            it.date
        }
        addDesignatedDate(copyMap)
        _uiState.update {
            it.copy(
                isComplete = false
            )
        }
    }

    fun deleteDesignatedDate() {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        val copyList = copyMap[_uiState.value.selectTabIndex]!!.toMutableList()

        copyList.filter {
            it.holidayName == _uiState.value.designatedDateName
        }.forEach {
            copyList.remove(it)
            deleteDesignatedDate(it)
        }
        copyMap[_uiState.value.selectTabIndex] = copyList
        updateShowDesignatedModal(false)
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    fun deleteAllDesignatedDate() {
        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[_uiState.value.selectTabIndex] = ArrayList()
        deleteAllDesignatedDate(_uiState.value.selectTabIndex)
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
        val value = copyMap[_uiState.value.selectTabIndex]
        copyList[index] = designateDateLabelName
        copyMap.remove(_uiState.value.selectTabIndex)
        if (!value.isNullOrEmpty()) {
            copyMap[_uiState.value.selectTabIndex] = value
        }
        updateDesignatedDateLabel(
            DesignatedDaysKeyEntity(
                _uiState.value.selectTabIndex,
                designateDateLabelName
            )
        )
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

        val copyList = if (copyMap[_uiState.value.selectTabIndex].isNullOrEmpty()) {
            mutableListOf()
        } else {
            copyMap[_uiState.value.selectTabIndex]!!.toMutableList()
        }
        for (value in result) {
            copyList.add(value)
        }
        copyList.distinctBy {
            it.date
        }

        copyMap[_uiState.value.selectTabIndex] = copyList.distinctBy { it.date }
        addDesignatedDate(copyMap)
    }

    private fun getDesignatedDate() = viewModelScope.launch {
        val result: List<DesignatedDaysKeyWithDesignatedDays> = getDesignatedDaysUseCase.invoke()
        val convertedResult = DesignatedDaysKeyWithDesignatedDays.convert(result)
        Timber.d("valender")

        val resultMap = mutableMapOf<Long, List<NationalHoliday>>()
        convertedResult.keys.forEach {
            val nationalHolidayList = convertedResult[it]?.map {
                NationalHoliday(
                    id = it.id,
                    date = it.designatedDate,
                    holidayName = it.designatedDateName,
                    keyId = _uiState.value.selectTabIndex
                )
            }
            resultMap[it] = nationalHolidayList!!
        }
        _uiState.update {
            it.copy(
                designatedDateMap = resultMap
            )
        }
    }

    private fun addDesignatedDate(param: Map<Long, List<NationalHoliday>>) = viewModelScope.launch {
        val rowIdList: List<Long> = addDesignatedDateUseCase.invoke(param)
        val lastIndex = rowIdList.size
        val nationalHolidayList: List<NationalHoliday> = param[_uiState.value.selectTabIndex]!!
        val startIndex = lastIndex - nationalHolidayList.size

        val newNationalHolidayList = nationalHolidayList.mapIndexed { index, nationalHoliday ->
            NationalHoliday(
                rowIdList[startIndex + index],
                nationalHoliday.date,
                nationalHoliday.holidayName,
                _uiState.value.selectTabIndex
            )
        }

        val copyMap = _uiState.value.designatedDateMap.toMutableMap()
        copyMap[_uiState.value.selectTabIndex] = newNationalHolidayList
        _uiState.update {
            it.copy(
                designatedDateMap = copyMap
            )
        }
    }

    private fun updateDesignatedDate(nationalHoliday: NationalHoliday) = viewModelScope.launch {
        updateDesignateDateUseCase.invoke(nationalHoliday)
    }

    private fun updateDesignatedDateLabel(designatedDaysKeyEntity: DesignatedDaysKeyEntity) =
        viewModelScope.launch {
            updateDesignatedDateLabelUseCase.invoke(designatedDaysKeyEntity)
        }

    private fun deleteDesignatedDate(nationalHoliday: NationalHoliday) = viewModelScope.launch {
        deleteDesignatedDateUseCase.invoke(nationalHoliday)
    }

    private fun deleteAllDesignatedDate(param: Long) = viewModelScope.launch {
        deleteAllDesignatedDateUseCase.invoke(param)
    }
}
