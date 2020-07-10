package com.pokotechnologies.beeceptor.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    @SerialName(value = "lon") val lon: Double,
    @SerialName(value = "lat") val lat: Double
    )

