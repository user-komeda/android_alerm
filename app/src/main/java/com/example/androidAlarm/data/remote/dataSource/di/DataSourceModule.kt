package com.example.androidAlarm.data.remote.dataSource.di

import com.example.androidAlarm.data.remote.dataSource.NationalHolidayDataSource
import com.example.androidAlarm.data.remote.dataSource.NationalHolidayDataSourceImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Provides
    @Singleton
    fun provideNationalHolidayDataSource(): NationalHolidayDataSource {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val okhttpFactory = OkHttpClient.Builder().build()

        return NationalHolidayDataSourceImpl(moshi, okhttpFactory)
    }
}
