package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.application.controller.response.CurrentWeather
import dev.yoghurt1131.weatherapi.application.controller.response.Forecast
import dev.yoghurt1131.weatherapi.application.controller.response.RainForecast

interface WeatherApiService {

    fun getCurrentWeather(cityName: String): CurrentWeather

    fun getTodayWeather(city: City): Forecast

    fun getRainForecast(city: City):List<RainForecast>
}
