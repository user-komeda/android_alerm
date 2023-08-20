package com.example.androidAlarm.data.repository

import com.example.androidAlarm.data.entity.DesignatedDaysKeyEntity

interface DesignatedDaysKeyRepository {

    suspend fun updateDesignatedDateLabel(designatedDaysKeyEntity: DesignatedDaysKeyEntity): Unit
}
