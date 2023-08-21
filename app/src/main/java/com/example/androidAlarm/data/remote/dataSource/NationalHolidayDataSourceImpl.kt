package com.example.androidAlarm.data.remote.dataSource

import com.example.androidAlarm.data.model.NationalHoliday
import com.squareup.moshi.Moshi
import okhttp3.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import timber.log.Timber
import javax.inject.Inject

private interface NationalHolidayApi {
    @GET(value = "nationalHolidays")
    suspend fun getNationalHoliday(): Response<List<NationalHoliday>>
}

class NationalHolidayDataSourceImpl @Inject constructor(
    moshi: Moshi,
    okhttpCallFactory: Call.Factory,
) : NationalHolidayDataSource {
    private val networkApi =
        Retrofit.Builder().baseUrl("http://10.0.2.2:3000").callFactory(okhttpCallFactory)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            ).build().create(NationalHolidayApi::class.java)

    override suspend fun getNationalHoliday(): Response<List<NationalHoliday>> {
        Timber.d(networkApi.getNationalHoliday().toString())
        return networkApi.getNationalHoliday()
    }
}
