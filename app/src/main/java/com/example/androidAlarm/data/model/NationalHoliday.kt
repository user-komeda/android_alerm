package com.example.androidAlarm.data.model

import com.example.androidAlarm.data.entity.DesignatedDaysEntity
import com.example.androidAlarm.model.DesignatedDateGroup
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NationalHoliday(
    @Json(name = "date") val date: String,
    @Json(name = "name") val holidayName: String
) {
//    companion object{
//        fun create():NationalHoliday{
//            return NationalHoliday()
//        }
//    }

    fun convertToDesignatedDaysEntity(designatedDateGroup: DesignatedDateGroup): DesignatedDaysEntity {
        return DesignatedDaysEntity(
            designatedDate = date,
            designatedDateName = holidayName,
            designatedDateGroup = designatedDateGroup
        )
    }
}
