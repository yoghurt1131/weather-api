package dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response

import com.fasterxml.jackson.annotation.JsonProperty
import dev.yoghurt1131.weatherapi.application.controller.response.RainForecast
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import dev.yoghurt1131.weatherapi.extentions.toDateTime

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
) {
    fun toRainForecast(city: City): RainForecast {
        return RainForecast(city, utcDatetime.toDateTime(), WeatherStatus.of(weatherDetailData.get(0).status))
    }
}


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
