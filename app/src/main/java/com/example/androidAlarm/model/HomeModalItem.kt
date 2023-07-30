@file:Suppress("MagicNumber")

package com.example.androidAlarm.model

enum class HomeModalItem(val viewItem: String, val alarmTime: Int) {
    ONE_MINUTE("1分", 1 * 60),
    TWO_MINUTE("2分", 2 * 60),
    THREE_MINUTE("3分", 3 * 60),
    FIVE_MINUTE("5分", 5 * 60),
    TEN_MINUTE("10分", 10 * 60),
    FIFTEEN_MINUTE("15分", 15 * 60),
    TWENTY_MINUTE("20分", 20 * 60),
    THIRTY_MINUTE("30分", 30 * 60),
    FORTY_FIVE_MINUTE("45分", 45 * 60),
    ONE_HOUR("1時間", 60 * 60),
    TWO_HOUR("2時間", 120 * 60),
    THREE_HOUR("3時間", 180 * 60),
    FOR_HOUR("4時間", 240 * 60),
    FIVE_HOUR("5時間", 300 * 60),
    SIX_HOUR("6時間", 360 * 60),
    SEVEN_HOUR("7時間", 420 * 60),
    EIGHT_HOUR("8時間", 480 * 60),
    NINE_HOUR("9時間", 540 * 60),
    TEN_HOUR("10時間", 600 * 60),
    ELEVEN_HOUR("11時間", 660 * 60),
    TWELVE_HOUR("12時間", 720 * 60),
}
