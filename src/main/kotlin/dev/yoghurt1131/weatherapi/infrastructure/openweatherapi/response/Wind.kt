package dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Wind(
    @JsonProperty("speed") private val speed: Float,
    @JsonProperty("deg") private val direction: Float
)
