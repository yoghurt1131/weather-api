package dev.yoghurt1131.weatherapi.infrastructure.weather.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * ValueObject for response of OpenWeatherAPI
 * weather forecast for 5 days with data every 3 hours by city name
 */
data class FiveDaysForecastResponse(
    @JsonProperty("list") val forecasts: List<RangedWeatherData>
)

data class RangedWeatherData(
        @JsonProperty("dt") val time: Long,
        @JsonProperty("dt_txt") val utcDatetime: String,
        @JsonProperty("main") var property: RangedWeatherProperty,
        @JsonProperty("weather") var weatherDetailData: List<WeatherDetailData>,
        @JsonProperty("clouds") var clouds: CloudnessData,
        @JsonProperty("wind") var wind: WindData
)


data class RangedWeatherProperty(
        @JsonProperty("temp") val temperature: Double,
        @JsonProperty("temp_min") val temperatureMin: Double,
        @JsonProperty("temp_max") val temperatureMax: Double,
        @JsonProperty("pressure") val pressure: Double,
        @JsonProperty("sea_level") val pressureOfSeaLevel: Double,
        @JsonProperty("gnd_level") val pressureOfGroundLevel: Double,
        @JsonProperty("humidity") val humidity: Double
)

data class CloudnessData(@JsonProperty("all") private val cloudness: Int)

data class WindData(
        @JsonProperty("speed") private val speed: Float,
        @JsonProperty("deg") private val direction: Float
)
