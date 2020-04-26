package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.Forecast
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.RangedWeatherData

/**
 * Apiレスポンスをもとに天気予報を作成する
 */
interface WeatherInterpreter {
    fun interpret(cityName: String, weatherData: List<RangedWeatherData>): Forecast
}
