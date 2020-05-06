package dev.yoghurt1131.weatherapi.application.controller.response

import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.domain.entity.WeatherStatus
import java.time.LocalDateTime

data class RainForecast(
        var city: City,
        var datetime: LocalDateTime,
        var status: WeatherStatus,
        // not implemented.
        var percentage: Int = 0
) {
}