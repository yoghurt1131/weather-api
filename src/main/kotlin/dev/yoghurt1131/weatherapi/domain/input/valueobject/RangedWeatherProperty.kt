package dev.yoghurt1131.weatherapi.domain.input.valueobject

import com.fasterxml.jackson.annotation.JsonProperty

data class RangedWeatherProperty(
        @JsonProperty("temp") val temperature: Double,
        @JsonProperty("temp_min") val temperatureMin: Double,
        @JsonProperty("temp_max") val temperatureMax: Double,
        @JsonProperty("pressure") val pressure: Double,
        @JsonProperty("sea_level") val pressureOfSeaLevel: Double,
        @JsonProperty("gnd_level") val pressureOfGroundLevel: Double,
        @JsonProperty("humidity") val humidity: Double
)