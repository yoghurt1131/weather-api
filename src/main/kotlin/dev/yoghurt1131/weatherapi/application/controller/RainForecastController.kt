package dev.yoghurt1131.weatherapi.application.controller

import dev.yoghurt1131.weatherapi.application.controller.response.RainForecast
import dev.yoghurt1131.weatherapi.application.service.WeatherApiService
import dev.yoghurt1131.weatherapi.domain.entity.City
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/rain-forecast")
class RainForecastController(private val weatherApiService: WeatherApiService) {

    /**
     * returns daily forecast of this week
     */
    @GetMapping("/daily")
    fun daily(cityName: String): List<RainForecast> {
        val city = City.valueOf(cityName)
        return weatherApiService.getRainForecast(city)
    }
}