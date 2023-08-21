package com.example.androidAlarm.data.model

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO dateの型をLocalDateTimeにしたい
@JsonClass(generateAdapter = true)
data class NationalHoliday(
    val id: Long = 0,
    @Json(name = "date") val date: String,
    @Json(name = "name") val holidayName: String,
    val keyId: Long = 0
) {
    companion object {
        fun convertToDesignatedDaysEntity(param: NationalHoliday): DesignatedDaysEntity {
            return DesignatedDaysEntity(
                id = param.id,
                designatedDate = param.date,
                designatedDateName = param.holidayName,
                designatedDaysKeyId = param.keyId
            )
        }
    }
}
