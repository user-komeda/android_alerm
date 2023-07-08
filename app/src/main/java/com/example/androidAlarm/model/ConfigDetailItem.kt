package com.example.androidAlarm.model

data class ConfigDetailItem(
    val itemList: List<String> = listOf(
        "アラーム音の遅延",
        "アラーム画面の文字の縮小",
        "アラーム画面にステータスバーを表示",
        "アラーム名の左寄せ表示",
        "アラーム画面の曲情報表示",
        "アラーム画面にスヌーズ回数表示",
        "アラーム重複時の自動停止",
        "アラーム起動時の時の音楽一時停止",
        "ロック画面の上にアラーム画面表示"
    )
)
