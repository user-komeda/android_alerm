package com.example.androidAlarm.ui.screens.designatedDate

import com.example.androidAlarm.model.DesignatedDateGroup

data class DesignatedDateState(
    val designatedDateMap: Map<DesignatedDateGroup, Map<String, String>> = mutableMapOf(
        DesignatedDateGroup.ONE_DESIGNATED_DATE to HashMap(),
        DesignatedDateGroup.TWO_DESIGNATED_DATE to HashMap(),
        DesignatedDateGroup.THREE_DESIGNATED_DATE to HashMap(),
        DesignatedDateGroup.FOR_DESIGNATED_DATE to HashMap(),
        DesignatedDateGroup.FIVE_DESIGNATED_DATE to HashMap(),
        DesignatedDateGroup.SIX_DESIGNATED_DATE to HashMap(),
        DesignatedDateGroup.SEVEN_DESIGNATED_DATE to HashMap(),
    ),
    val selectTabIndex: Int = 0
)
