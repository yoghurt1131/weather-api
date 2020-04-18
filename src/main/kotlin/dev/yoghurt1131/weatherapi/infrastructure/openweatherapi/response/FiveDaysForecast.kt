package dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * ValueObject for response of OpenWeatherAPI
 * weather forecast for 5 days with data every 3 hours by city name
 */
data class FiveDaysForecast(
    @JsonProperty("cod") val code: String,
    @JsonProperty("cnt") val cound: Int,
    @JsonProperty("list") val forecasts: List<RangedWeather>
)
