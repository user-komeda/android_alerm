package com.example.androidAlarm

enum class AlarmDestination {
    HOME,
    HOME_DETAIL,
    CONFIG,
    CONFIG_DETAIL,
    DESIGNATED_DATE;

    companion object {
        fun fromRoute(route: String?): AlarmDestination {
            return when (route?.substringBefore("/")) {
                HOME.name -> HOME
                HOME_DETAIL.name -> HOME_DETAIL
                CONFIG.name -> CONFIG
                CONFIG_DETAIL.name -> CONFIG_DETAIL
                DESIGNATED_DATE.name -> DESIGNATED_DATE
                else -> HOME
            }
        }
    }
}
