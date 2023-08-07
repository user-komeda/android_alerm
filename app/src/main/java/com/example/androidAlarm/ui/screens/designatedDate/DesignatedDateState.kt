package com.example.androidAlarm.ui.screens.designatedDate

import com.example.androidAlarm.data.model.NationalHoliday
import com.example.androidAlarm.model.DesignatedDateGroup

data class DesignatedDateState(
    val designatedDateMap: Map<DesignatedDateGroup, List<NationalHoliday>> = mutableMapOf(
        DesignatedDateGroup.ONE_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
        DesignatedDateGroup.TWO_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
        DesignatedDateGroup.THREE_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
        DesignatedDateGroup.FOR_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
        DesignatedDateGroup.FIVE_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
        DesignatedDateGroup.SIX_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
        DesignatedDateGroup.SEVEN_DESIGNATED_DATE to ArrayList<NationalHoliday>(),
    ),
    val selectTabIndex: Int = 0,
    val isShowDesignatedDateModal: Boolean = false
)
