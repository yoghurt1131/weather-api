package dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherDetailData(
        @JsonProperty("id") val id: Int,
        @JsonProperty("main") val status: String,
        @JsonProperty("description")val description: String,
        @JsonProperty("icon") val icon: String,
        @JsonProperty("weatherIconUrl") private val weatherIconUrl: String?
) {

    private val WEATHER_ICON_URL = "http://openweathermap.org/img/w/"

    fun getWeatherIconUrl() = "$WEATHER_ICON_URL$icon.png"
}

