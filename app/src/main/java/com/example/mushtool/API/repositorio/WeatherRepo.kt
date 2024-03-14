package com.example.mushtool.API.repositorio

import com.example.mushtool.API.models.BaseModel
import com.example.mushtool.API.models.DailyForecasts
import com.example.mushtool.API.models.HourlyForecast
import com.example.mushtool.API.models.Location

interface WeatherRepo {
    suspend fun searchLocation(query:String):BaseModel<List<Location>>
    suspend fun getDailyForecasts(locationKey:String): BaseModel<DailyForecasts>
    suspend fun getHourlyForecasts(locationKey: String):BaseModel<List<HourlyForecast>>
}
