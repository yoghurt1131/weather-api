package dev.yoghurt1131.weatherapi.application.controller

import dev.yoghurt1131.weatherapi.application.service.WeatherApiService
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.application.controller.response.Forecast
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/forecast")
class ForecastController(private val weatherApiService: WeatherApiService) {

    @GetMapping("/today")
    fun todayForecast(cityName: String): Forecast {
        val city = City.valueOf(cityName)
        return weatherApiService.getTodayWeather(city)
    }

}
