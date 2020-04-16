package dev.yoghurt1131.weatherapi.domain.input.valueobject

import com.fasterxml.jackson.annotation.JsonProperty

data class Wind(
    @JsonProperty("speed") private val speed: Float,
    @JsonProperty("deg") private val direction: Float
)
