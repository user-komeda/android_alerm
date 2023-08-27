package com.example.androidAlarm.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.concurrent.Immutable

@Entity(tableName = "DesignatedDaysKey")
@Immutable
data class DesignatedDaysKeyEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo val designatedDaysKeyName: String = ""
)
