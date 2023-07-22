@file:Suppress("MagicNumber")

package com.example.androidAlarm.model

enum class HomeModalItem(val viewItem: String, val alarmTime: Int) {
    ONE_MINUTE("1分", 1),
    TWO_MINUTE("2分", 2),
    THREE_MINUTE("3分", 3),
    FIVE_MINUTE("5分", 5),
    TEN_MINUTE("10分", 10),
    FIFTEEN_MINUTE("15分", 15),
    TWENTY_MINUTE("20分", 20),
    THIRTY_MINUTE("30分", 30),
    FORTY_FIVE_MINUTE("45分", 45),
    ONE_HOUR("1時間", 60),
    TWO_HOUR("2時間", 120),
    THREE_HOUR("3時間", 180),
    FOR_HOUR("4時間", 240),
    FIVE_HOUR("5時間", 300),
    SIX_HOUR("6時間", 360),
    SEVEN_HOUR("7時間", 420),
    EIGHT_HOUR("8時間", 480),
    NINE_HOUR("9時間", 540),
    TEN_HOUR("10時間", 600),
    ELEVEN_HOUR("11時間", 660),
    TWELVE_HOUR("12時間", 720),
}
