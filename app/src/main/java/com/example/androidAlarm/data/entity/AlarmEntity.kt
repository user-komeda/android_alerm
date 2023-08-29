package com.example.androidAlarm.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.androidAlarm.model.Alarm
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "alarm",
    indices = [Index(value = ["alarmRequestCode"], unique = true)]
)
@Immutable
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "alarmTime") val alarmTime: String,
    @ColumnInfo(name = "alarmRequestCode") val alarmRequestCode: Int = 0,
    @ColumnInfo(name = "isEnable") val isEnable: Boolean
) {
    companion object {
        fun build(alarm: Alarm): AlarmEntity {
            return AlarmEntity(
                alarmTime = alarm.alarmClock,
                alarmRequestCode = alarm.alarmRequestCode,
                isEnable = alarm.isEnable
            )
        }

        fun toModelAlarm(alarmEntity: AlarmEntity): Alarm {
            return Alarm(
                alarmEntity.id,
                alarmEntity.alarmTime,
                alarmEntity.alarmRequestCode,
                alarmEntity.isEnable
            )
        }
    }
}
