package dev.yoghurt1131.weatherapi.infrastructure.weather.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Wind(
    @JsonProperty("speed") private val speed: Float,
    @JsonProperty("deg") private val direction: Float
)
