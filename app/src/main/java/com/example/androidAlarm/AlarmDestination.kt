package com.example.androidAlarm

enum class AlarmDestination {
    HOME,
    HOME_DETAIL,
    CONFIG,
    CONFIG_DETAIL,
    DESIGNATED_DATE,
    CALENDAR,
    ALARM_TIME,
    ALARM_TIME_DETAIL;

    companion object {
        fun fromRoute(route: String?): AlarmDestination {
            return when (route?.substringBefore("/")) {
                HOME.name -> HOME
                HOME_DETAIL.name -> HOME_DETAIL
                CONFIG.name -> CONFIG
                CONFIG_DETAIL.name -> CONFIG_DETAIL
                DESIGNATED_DATE.name -> DESIGNATED_DATE
                CALENDAR.name -> CALENDAR
                ALARM_TIME.name -> ALARM_TIME
                ALARM_TIME_DETAIL.name -> ALARM_TIME_DETAIL
                else -> HOME
            }
        }
    }
}
