package com.pokotechnologies.beeceptor.app.utils

import com.pokotechnologies.beeceptor.app.model.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceUtil {

    @GET(value = "weather?")
    suspend fun weatherState(
        @Query(value = "q") city: String,
        @Query(value = "appid") apiKey: String,
        @Query(value = "charset") charset: String,
        @Query(value = "units")units:String
    ): WeatherForecast
}

