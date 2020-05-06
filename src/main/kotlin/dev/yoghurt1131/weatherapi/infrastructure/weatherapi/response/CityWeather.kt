package dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import dev.yoghurt1131.weatherapi.application.controller.response.CurrentWeather

data class CityWeather(
        val name: String,
        @JsonProperty("weather")
    val weatherDetailData: List<WeatherDetailData>,
        @JsonProperty("main")
    val temperature: TemperatureData
) {

        fun extractWeather() = if (weatherDetailData.isNotEmpty()) weatherDetailData.first().status else ""

        fun extractKelvin() = temperature.temp

        @JsonIgnore
        fun getCurrentWeather(): CurrentWeather {
                var currentWeather: CurrentWeather =
                        CurrentWeather(name, extractWeather(), extractKelvin(), weatherDetailData.first().getWeatherIconUrl())
                return currentWeather
        }

        fun buildWeather() = CurrentWeather(
                name, extractWeather(), extractKelvin(), weatherDetailData.get(0).getWeatherIconUrl()
        )
}
