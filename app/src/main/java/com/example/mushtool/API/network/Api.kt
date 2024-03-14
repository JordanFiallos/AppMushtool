package com.example.mushtool.API.network

import com.example.mushtool.API.models.DailyForecasts
import com.example.mushtool.API.models.HourlyForecast
import com.example.mushtool.API.models.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "mjMhwRed9JmzVefTewAeOF7oZdAEAgmx"
interface Api {
    @GET("locations/v1/cities/search")
    suspend fun searchLocation(
        @Query("apikey") apiKey:String = API_KEY,
        @Query("q") query: String
    ): Response<List<Location>>

    @GET("forecasts/v1/daily/5day/{location_key}")
    suspend fun getDailyForecasts(
        @Path("location_key") locationkey:String,
        @Query("apikey") apiKey:String = API_KEY,
        @Query("metric") metric:Boolean = true,
    ):Response<DailyForecasts>

    @GET("forecasts/v1/hourly/12hour/{location_key}")
    suspend fun getHourlyForecasts(
        @Path("location_key") locationkey:String,
        @Query("apikey") apiKey:String = API_KEY,
        @Query("metric") metric:Boolean = true,
    ):Response<List<HourlyForecast>>
}