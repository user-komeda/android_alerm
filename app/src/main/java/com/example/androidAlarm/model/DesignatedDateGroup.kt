@file:Suppress("MagicNumber")
package com.example.androidAlarm.model

enum class DesignatedDateGroup(val value: String) {
    ONE_DESIGNATED_DATE("第一指定日"),
    TWO_DESIGNATED_DATE("第二指定日"),
    THREE_DESIGNATED_DATE("第三指定日"),
    FOR_DESIGNATED_DATE("第四指定日"),
    FIVE_DESIGNATED_DATE("第五指定日"),
    SIX_DESIGNATED_DATE("第六指定日"),
    SEVEN_DESIGNATED_DATE("第七指定日"),
    EIGHT_DESIGNATED_DATE("第八指定日");

    companion object {
        fun getDesignatedGroupFromTabIndex(selectTabIndex: Int): DesignatedDateGroup {
            return when (selectTabIndex) {
                1 -> ONE_DESIGNATED_DATE
                2 -> TWO_DESIGNATED_DATE
                3 -> THREE_DESIGNATED_DATE
                4 -> FOR_DESIGNATED_DATE
                5 -> FIVE_DESIGNATED_DATE
                6 -> SIX_DESIGNATED_DATE
                7 -> SIX_DESIGNATED_DATE
                8 -> EIGHT_DESIGNATED_DATE
                else -> {
                    ONE_DESIGNATED_DATE
                }
            }
        }
    }
}
