package dev.yoghurt1131.weatherapi.infrastructure.openweatherapi

import dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response.CityWeather
import org.springframework.web.client.RestTemplate

class CurrentWeatherWrapper(
        apiUrl: String,
        apiKey: String,
        restTemplate: RestTemplate,
        type: Class<CityWeather>
)  : CityRequestWrapper<CityWeather>(apiUrl, apiKey, restTemplate, type) {
}