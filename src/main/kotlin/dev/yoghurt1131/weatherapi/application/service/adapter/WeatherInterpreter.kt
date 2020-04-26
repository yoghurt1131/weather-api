package dev.yoghurt1131.weatherapi.application.service.adapter

import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.Forecast
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.RangedWeatherData

/**
 * Create Weather Information from Api Response for Anti-Corruption.
 */
interface WeatherInterpreter {

    fun toTodaysForecast(city: City, weatherData: List<RangedWeatherData>): Forecast

    fun toDailyForecast(city: City): List<Forecast>
}
