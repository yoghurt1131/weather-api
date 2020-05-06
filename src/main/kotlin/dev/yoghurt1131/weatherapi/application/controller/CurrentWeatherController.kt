package dev.yoghurt1131.weatherapi.application.controller

import dev.yoghurt1131.weatherapi.application.service.WeatherApiService
import dev.yoghurt1131.weatherapi.application.controller.response.CurrentWeather
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/current")
@RestController
class CurrentWeatherController(private val weatherApiService: WeatherApiService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/city")
    fun city(cityName: String): CurrentWeather = weatherApiService.getCurrentWeather(cityName)
}
