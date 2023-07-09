package com.example.androidAlarm.ui.screens.designatedDate

import com.example.androidAlarm.model.DesignatedDateGroup

data class DesignatedDateState(
    val designatedDateMap: Map<DesignatedDateGroup, List<String>> = mutableMapOf(
        DesignatedDateGroup.ONE_DESIGNATED_DATE to mutableListOf<String>("a", "b", "c"),
        DesignatedDateGroup.TWO_DESIGNATED_DATE to mutableListOf<String>("d", "e", "f"),
        DesignatedDateGroup.THREE_DESIGNATED_DATE to mutableListOf<String>("g", "h", "i"),
        DesignatedDateGroup.FOR_DESIGNATED_DATE to ArrayList(),
        DesignatedDateGroup.FIVE_DESIGNATED_DATE to ArrayList(),
        DesignatedDateGroup.SIX_DESIGNATED_DATE to ArrayList(),
        DesignatedDateGroup.SEVEN_DESIGNATED_DATE to ArrayList(),
    ),
    val selectTabIndex: Int = 0
)
