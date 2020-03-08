package dev.yoghurt1131.weatherapi.service

import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast

/**
 * Apiレスポンスをもとに天気予報を作成する
 */
interface WeatherInterpreter {
    fun interpret(weatherData: List<RangedWeather>): Forecast;
}