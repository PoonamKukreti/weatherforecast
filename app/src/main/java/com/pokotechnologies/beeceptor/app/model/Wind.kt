package com.pokotechnologies.beeceptor.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Wind(
    @SerialName(value = "deg") val deg: Double,
    @SerialName(value = "speed") val speed: Double
)
