package com.example.androidAlarm.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidAlarm.model.Alarm
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "alarm",
)
@Immutable
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "alarmTime") val alarmTime: String,
    @ColumnInfo(name = "isEnable") val isEnable: Boolean
) {
    companion object {
        fun build(alarm: Alarm): AlarmEntity {
            return AlarmEntity(alarmTime = alarm.alarmClock, isEnable = alarm.isEnable)
        }

        fun toModelAlarm(alarmEntity: AlarmEntity): Alarm {
            return Alarm(alarmEntity.id, alarmEntity.alarmTime, alarmEntity.isEnable)
        }
    }
}
