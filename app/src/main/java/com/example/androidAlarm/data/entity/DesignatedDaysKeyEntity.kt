package com.example.androidAlarm.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DesignatedDaysKey")
data class DesignatedDaysKeyEntity(
    @PrimaryKey() @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo val designatedDaysKeyName: String = ""
)
