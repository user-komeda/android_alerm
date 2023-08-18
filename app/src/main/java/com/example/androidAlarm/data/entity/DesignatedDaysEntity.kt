@file:Suppress("ExplicitItLambdaParameter")

package com.example.androidAlarm.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.androidAlarm.data.model.NationalHoliday
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "designatedDate",
    foreignKeys = [
        ForeignKey(
            entity = DesignatedDaysKeyEntity::class,
            parentColumns = ["id"],
            childColumns = ["designatedDaysKeyId"]
        )
    ],
    indices = [Index(value = ["designatedDate", "designatedDaysKeyId"], unique = true)]

)
@Immutable
data class DesignatedDaysEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "designatedDate") val designatedDate: String = "",
    @ColumnInfo(name = "designatedDateName") val designatedDateName: String = "",
    @ColumnInfo(name = "designatedDaysKeyId") val designatedDaysKeyId: String = ""

) {

    companion object {
        fun build(param: Map<String, List<NationalHoliday>>): List<DesignatedDaysEntity> {
            val result = mutableListOf<DesignatedDaysEntity>()
            param.keys.forEach { it ->
                val key = it
                param[it]?.forEach {
                    result.add(
                        DesignatedDaysEntity(
                            designatedDate = it.date,
                            designatedDateName = it.holidayName,
                            designatedDaysKeyId = key
                        )
                    )
                }
            }
            return result
        }
    }

    fun convertToNationalHoliday(): NationalHoliday {
        return NationalHoliday(designatedDate, designatedDateName)
    }
}
