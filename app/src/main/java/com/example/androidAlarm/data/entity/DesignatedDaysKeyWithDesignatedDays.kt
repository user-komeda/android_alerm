package com.example.androidAlarm.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DesignatedDaysKeyWithDesignatedDays(
    @Embedded val designatedDaysKeyEntity: DesignatedDaysKeyEntity,
    @Relation(parentColumn = "id", entityColumn = "designatedDaysKeyId")
    val designatedDaysEntityList: List<DesignatedDaysEntity>
) {

    companion object {
        fun convert(params: List<DesignatedDaysKeyWithDesignatedDays>): Map<Long, List<DesignatedDaysEntity>> {
            return params.associateBy(
                { it.designatedDaysKeyEntity.id },
                { it.designatedDaysEntityList }
            )
        }
    }
}
