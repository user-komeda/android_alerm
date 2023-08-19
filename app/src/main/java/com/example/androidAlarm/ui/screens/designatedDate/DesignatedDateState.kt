@file:Suppress("MagicNumber")

package com.example.androidAlarm.ui.screens.designatedDate

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.androidAlarm.data.model.NationalHoliday
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class DesignatedDateState constructor(
    val designatedDateMapKeyList: List<String> = mutableListOf(
        "第一指定日",
        "第二指定日",
        "第三指定日",
        "第四指定日",
        "第五指定日",
        "第六指定日",
        "第七指定日"
    ),
    val designatedDateMap: Map<Long, List<NationalHoliday>> = mutableMapOf(
        1L to ArrayList<NationalHoliday>(),
        2L to ArrayList<NationalHoliday>(),
        3L to ArrayList<NationalHoliday>(),
        4L to ArrayList<NationalHoliday>(),
        5L to ArrayList<NationalHoliday>(),
        6L to ArrayList<NationalHoliday>(),
        7L to ArrayList<NationalHoliday>(),
    ),
    val selectTabIndex: Long = 1,
    val isShowDesignatedDateModal: Boolean = false,
//    TODO下記二つをNationalHolidayObjectにまとめる
    val designatedDate: LocalDateTime = LocalDateTime.now(),
    val designatedDateName: String = "",
    val isShowDataTimePicker: Boolean = false,
    val isShowDataTimePicker2: Boolean = false,
    val isShowDataTimePicker3: Boolean = false,
    val isComplete: Boolean = false,
    val isShowAddDesignatedDateModal: Boolean = false,
    val isShowDesignatedDateLabelModal: Boolean = false,
    val selectDesignatedDateLabel: String = "",
    val editTextDesignatedDateLabel: String = "",
    val isShowEditDesignatedDateLabelModal: Boolean = false,
    val isOkEditDesignatedDateLabelModal: Boolean = false
)
