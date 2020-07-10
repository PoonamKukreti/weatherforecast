package com.pokotechnologies.beeceptor.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    @SerialName(value = "country") val country: String,
    @SerialName(value = "sunrise") val sunrise: Int,
    @SerialName(value = "sunset") val sunset: Int
)
