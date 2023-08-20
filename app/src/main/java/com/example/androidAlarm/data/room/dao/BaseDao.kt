package com.example.androidAlarm.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg entity: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: Collection<T>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)
}
