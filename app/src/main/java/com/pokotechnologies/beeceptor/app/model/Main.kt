package com.pokotechnologies.beeceptor.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Main(
    @SerialName(value = "temp") val temp: Int,
    @SerialName(value = "feels_like") val feels_like: Double,
    @SerialName(value = "temp_min") val temp_min: Int,
    @SerialName(value = "temp_max") val temp_max: Int,
    @SerialName(value = "pressure") val pressure: Int,
    @SerialName(value = "humidity") val humidity: Int
)
