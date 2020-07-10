package com.pokotechnologies.beeceptor.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class WeatherItem(
    @SerialName(value = "icon") val icon: String,
    @SerialName(value = "description") val description: String,
    @SerialName(value = "main") val main: String,
    @SerialName(value = "id") val id: Int
)

