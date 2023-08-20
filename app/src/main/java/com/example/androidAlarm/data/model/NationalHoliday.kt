package com.example.androidAlarm.data.model

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO dateの型をLocalDateTimeにしたい
@JsonClass(generateAdapter = true)
data class NationalHoliday(
    val id: Long = 0,
    @Json(name = "date") val date: String,
    @Json(name = "name") val holidayName: String
) {
    companion object {
        fun convertToDesignatedDaysEntity(param: NationalHoliday): DesignatedDaysEntity {
            return DesignatedDaysEntity(
//                TODO deleteするとIDが変わってしまう
                id = param.id,
                designatedDate = param.date,
                designatedDateName = param.holidayName,
                designatedDaysKeyId = 1
            )
        }
    }
}
