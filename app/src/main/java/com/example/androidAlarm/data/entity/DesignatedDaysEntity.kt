package com.example.androidAlarm.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidAlarm.model.DesignatedDateGroup
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "designatedDate"
)
@Immutable
data class DesignatedDaysEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "designatedDate") val designatedDate: String,
    @ColumnInfo(name = "designatedDateName") val designatedDateName: String,
    @ColumnInfo(name = "designatedDateGroup") val designatedDateGroup: DesignatedDateGroup

)
