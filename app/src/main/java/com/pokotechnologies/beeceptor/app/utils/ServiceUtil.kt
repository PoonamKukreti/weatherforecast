package com.pokotechnologies.beeceptor.app.utils

import com.pokotechnologies.beeceptor.app.model.WeatherForecast
import retrofit2.http.Query

    interface ServiceUtil {
        suspend fun weatherState(
            @Query(
                value = "appid"
            ) apiKey: String, @Query(value = "q") city: String
        ): WeatherForecast
    }

