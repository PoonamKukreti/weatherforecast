package com.pokotechnologies.beeceptor.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecast(
    @SerialName(value = "coord") val coord: Coord?,
    @SerialName(value = "weather") val weather:  List<WeatherItem>?,
    @SerialName(value = "base") val base: String?,
    @SerialName(value = "main") val main: Main?,
    @SerialName(value = "wind") val wind: Wind?,
    @SerialName(value = "clouds") val clouds: Clouds?,
    @SerialName(value = "timezone") val timezone: Int?,
    @SerialName(value = "dt") val dt: Int?,
    @SerialName(value = "sys") val sys: Sys?,
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "cod") val cod: Int?
    )