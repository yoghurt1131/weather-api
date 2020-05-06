package dev.yoghurt1131.weatherapi.application.controller.response

import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import dev.yoghurt1131.weatherapi.domain.entity.City

data class Forecast(
        var city: City,
        var status: WeatherStatus,
        var weatherIconUrl: String,
        var maxTemperature: Double,
        var minTemperature: Double
)
