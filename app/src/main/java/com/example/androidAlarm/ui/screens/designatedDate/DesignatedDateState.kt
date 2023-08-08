@file:Suppress("MagicNumber")

package com.example.androidAlarm.ui.screens.designatedDate

import com.example.androidAlarm.data.model.NationalHoliday

data class DesignatedDateState(
    val designatedDateMapKeyList: List<String> = mutableListOf(
        "第一指定日",
        "第二指定日",
        "第三指定日",
        "第四指定日",
        "第五指定日",
        "第一指定日",
        "第七指定日"
    ),
    val designatedDateMap: Map<String, List<NationalHoliday>> = mutableMapOf(
        designatedDateMapKeyList[0] to ArrayList<NationalHoliday>(),
        designatedDateMapKeyList[1] to ArrayList<NationalHoliday>(),
        designatedDateMapKeyList[2] to ArrayList<NationalHoliday>(),
        designatedDateMapKeyList[3] to ArrayList<NationalHoliday>(),
        designatedDateMapKeyList[4] to ArrayList<NationalHoliday>(),
        designatedDateMapKeyList[5] to ArrayList<NationalHoliday>(),
        designatedDateMapKeyList[6] to ArrayList<NationalHoliday>(),
    ),
    val selectTabIndex: Int = 0,
    val isShowDesignatedDateModal: Boolean = false,
    val selectedDate: String = "",
    val isShowDataTimePicker: Boolean = false,
    val designatedDateName: String = ""
)
