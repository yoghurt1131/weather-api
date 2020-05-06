package dev.yoghurt1131.weatherapi.application.service.adapter

import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.application.controller.response.Forecast
import dev.yoghurt1131.weatherapi.application.controller.response.RainForecast
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.RangedWeatherData

/**
 * Create Weather Information from Api Response for Anti-Corruption.
 */
interface WeatherInterpreter {

    fun toTodaysForecast(city: City, weatherData: List<RangedWeatherData>): Forecast

    fun toDailyRainForecast(city: City, weatherData: List<RangedWeatherData>): List<RainForecast>
}
