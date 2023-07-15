package com.example.androidAlarm.data.remote.dataSource
import com.squareup.moshi.Moshi
import okhttp3.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

private interface NationalHolidayApi {
    @GET(value = "api/v1/date.json")
    suspend fun getNationalHoliday(): Response<Map<String, String>>
}

class NationalHolidayDataSourceImpl @Inject constructor(
    moshi: Moshi,
    okhttpCallFactory: Call.Factory,
) : NationalHolidayDataSource {
    private val networkApi =
        Retrofit.Builder().baseUrl("https://holidays-jp.github.io/").callFactory(okhttpCallFactory)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            ).build().create(NationalHolidayApi::class.java)

    override suspend fun getNationalHoliday(): Response<Map<String, String>> {
        return networkApi.getNationalHoliday()
    }
}
