package dev.yoghurt1131.weatherapi.domain.input.valueobject

import com.fasterxml.jackson.annotation.JsonProperty

data class RangedWeather(
    @JsonProperty("dt") val time: Long,
    @JsonProperty("dt_txt") val utcDatetime: String,
    @JsonProperty("main") var property: RangedWeatherProperty,
    @JsonProperty("weather") var weathers: List<Weather>,
    @JsonProperty("clouds") var clouds: Clouds,
    @JsonProperty("wind") var wind: Wind
)
